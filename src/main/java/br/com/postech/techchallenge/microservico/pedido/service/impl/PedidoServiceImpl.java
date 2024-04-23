package br.com.postech.techchallenge.microservico.pedido.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.postech.techchallenge.microservico.pedido.comum.converts.StatusPedidoParaInteiroConverter;
import br.com.postech.techchallenge.microservico.pedido.comum.enums.StatusPedidoEnum;
import br.com.postech.techchallenge.microservico.pedido.comum.util.CpfCnpjUtil;
import br.com.postech.techchallenge.microservico.pedido.configuration.ModelMapperConfiguration;
import br.com.postech.techchallenge.microservico.pedido.entity.Cliente;
import br.com.postech.techchallenge.microservico.pedido.entity.Pedido;
import br.com.postech.techchallenge.microservico.pedido.entity.PedidoProduto;
import br.com.postech.techchallenge.microservico.pedido.entity.Produto;
import br.com.postech.techchallenge.microservico.pedido.exception.BusinessException;
import br.com.postech.techchallenge.microservico.pedido.exception.NotFoundException;
import br.com.postech.techchallenge.microservico.pedido.model.request.PagamentoRequest;
import br.com.postech.techchallenge.microservico.pedido.model.request.PedidoRequest;
import br.com.postech.techchallenge.microservico.pedido.model.response.PedidoResponse;
import br.com.postech.techchallenge.microservico.pedido.repository.ClienteJpaRepository;
import br.com.postech.techchallenge.microservico.pedido.repository.PedidoJpaRepository;
import br.com.postech.techchallenge.microservico.pedido.repository.ProdutoJpaRepository;
import br.com.postech.techchallenge.microservico.pedido.service.PedidoService;
import br.com.postech.techchallenge.microservico.pedido.service.integracao.PagamentoApiService;
import lombok.RequiredArgsConstructor;


@Service
@Transactional
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
	private static final ModelMapper MAPPER = ModelMapperConfiguration.getModelMapper();

	private final PedidoJpaRepository pedidoJpaRepository;
	private final ClienteJpaRepository clienteJpaRepository;
	private final ProdutoJpaRepository produtoJpaRepository;
	private final PagamentoApiService pagamentoApiService;

	@Override
	public List<PedidoResponse> findTodosPedidosAtivos()throws BusinessException{
		List<Pedido> pedidos = pedidoJpaRepository
				.findByStatusPedidoNotIn(
						Arrays.asList(
								StatusPedidoEnum.PRONTO,
								StatusPedidoEnum.FINALIZADO)
				);

		MAPPER.typeMap(Pedido.class, PedidoResponse.class)
		.addMappings(mapperA -> mapperA
				.using(new StatusPedidoParaInteiroConverter())
					.map(Pedido::getStatusPedido, PedidoResponse::setStatusPedido))
		.addMappings(mapper -> {
			  mapper.map(src -> src.getId(),PedidoResponse::setNumeroPedido);
		});

		return MAPPER.map(pedidos, new TypeToken<List<PedidoResponse>>() {}.getType());
	}

	@Override
	public PedidoResponse findById(Integer id) throws Exception{
		pagamentoApiService.consultarPagamentoPorPedido(Long.valueOf(id));
		
		Pedido pedido = pedidoJpaRepository
				.findById(id)
				.orElseThrow(() -> new NotFoundException("Pedido não encontrado!"));

		return MAPPER.map(pedido, PedidoResponse.class);
	}

	@Override
	public PedidoResponse fazerPedidoFake(PedidoRequest pedidoRequest) throws BusinessException {
		//Obtem os dados do pedido
		Pedido pedido = MAPPER.map(pedidoRequest, Pedido.class);
		pedido.setStatusPedido(StatusPedidoEnum.get(pedidoRequest.statusPedido()));
		pedido.setDataPedido(LocalDateTime.now());
		pedido.setStatusPedido(StatusPedidoEnum.RECEBIDO);

		valideCliente(pedido);

		valideProduto(pedido);

		//Salva o pedido e obtem seu numero
		pedido = pedidoJpaRepository.save(pedido);
		
		//Obtem o valor total do pedido
		BigDecimal valorPedido = pedido.getProdutos()
				.stream()
				.map(PedidoProduto::total)
				.reduce((x, y) -> x.add(y))
				.get();
		
		//Cria um registro de pagamento no banco como Pendente
		PagamentoRequest pagamento = new PagamentoRequest(null, pedido.getId(), 1, valorPedido);
		
		pagamentoApiService.criarPagamento(pagamento);

		MAPPER.typeMap(Pedido.class, PedidoResponse.class)
		.addMappings(mapperA -> mapperA
				.using(new StatusPedidoParaInteiroConverter())
					.map(Pedido::getStatusPedido, PedidoResponse::setStatusPedido))
		.addMappings(mapper -> {
			  mapper.map(src -> src.getId(),PedidoResponse::setNumeroPedido);
		});

		return MAPPER.map(pedido, PedidoResponse.class);
	}

	private void valideProduto(Pedido pedido)  throws BusinessException{
		//Verifica se o está cadastrado produtos
		Optional.ofNullable(pedido.getProdutos())
			.orElseThrow(() -> new BusinessException("É obrigatório informar algum produto!"))
			.stream()
			.filter(pedidoProduto -> Objects.nonNull(pedidoProduto.getProduto()) &&
								     Objects.nonNull(pedidoProduto.getProduto().getId()))
			.findAny()
			.orElseThrow(() -> new BusinessException("Produto não cadastrado!"));

		//Atribui atualiza lista de pedido_produto.
		pedido.getProdutos()
			.stream()
			.distinct()
			.forEach(pedidoProduto -> {
					pedidoProduto.setPedido(pedido);
					Produto produto = produtoJpaRepository.findById(pedidoProduto.getProduto().getId()).get();
					pedidoProduto.setProduto(produto);
			});
	}

	private void valideCliente(Pedido pedido) throws BusinessException{
		//Caso informe dados do cliente, é obrigatorio o cliente existir
		if(Objects.nonNull(pedido.getCliente())) {
			pedido.getCliente().setCpf(CpfCnpjUtil.removeMaskCPFCNPJ(pedido.getCliente().getCpf()));

			Cliente cliente = clienteJpaRepository.findByCpfOrNomeOrEmail(pedido.getCliente().getCpf(),
					pedido.getCliente().getNome(), pedido.getCliente().getEmail());

			if(Objects.isNull(cliente)) {
				throw new BusinessException("Cliente não encontrado!");
			}

			pedido.setCliente(cliente);
		}
	}

}
