package br.com.postech.techchallenge.microservico.core.pedido.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.postech.techchallenge.microservico.core.comum.enums.StatusPedidoEnum;
import br.com.postech.techchallenge.microservico.core.comum.util.CpfCnpjUtil;
import br.com.postech.techchallenge.microservico.core.pedido.configuration.InteiroParaStatusPedidoConverter;
import br.com.postech.techchallenge.microservico.core.pedido.configuration.ModelMapperConfiguration;
import br.com.postech.techchallenge.microservico.core.pedido.configuration.StatusPedidoParaInteiroConverter;
import br.com.postech.techchallenge.microservico.core.pedido.entity.Cliente;
import br.com.postech.techchallenge.microservico.core.pedido.entity.Pedido;
import br.com.postech.techchallenge.microservico.core.pedido.entity.Produto;
import br.com.postech.techchallenge.microservico.core.pedido.exception.BusinessException;
import br.com.postech.techchallenge.microservico.core.pedido.exception.NotFoundException;
import br.com.postech.techchallenge.microservico.core.pedido.model.request.PedidoRequest;
import br.com.postech.techchallenge.microservico.core.pedido.model.response.PedidoResponse;
import br.com.postech.techchallenge.microservico.core.pedido.repository.ClienteJpaRepository;
import br.com.postech.techchallenge.microservico.core.pedido.repository.PedidoJpaRepository;
import br.com.postech.techchallenge.microservico.core.pedido.repository.ProdutoJpaRepository;
import br.com.postech.techchallenge.microservico.core.pedido.service.PedidoService;
import lombok.RequiredArgsConstructor;


@Service
@Transactional
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
	private static final ModelMapper MAPPER = ModelMapperConfiguration.getModelMapper();

	private final PedidoJpaRepository pedidoJpaRepository;
	private final ClienteJpaRepository clienteJpaRepository;
	private final ProdutoJpaRepository produtoJpaRepository;

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
	public PedidoResponse findById(Integer id) throws BusinessException{
		Pedido pedido = pedidoJpaRepository
				.findById(id)
				.orElseThrow(() -> new NotFoundException("Pedido não encontrado!"));

		return MAPPER.map(pedido, PedidoResponse.class);
	}

	@Override
	public PedidoResponse fazerPedidoFake(PedidoRequest pedidoRequest) throws BusinessException {
		//Obtem os dados do pedido
		MAPPER.typeMap(PedidoRequest.class, Pedido.class)
			.addMappings(mapperA -> mapperA
					.using(new InteiroParaStatusPedidoConverter())
						.map(PedidoRequest::statusPedido, Pedido::setStatusPedido));

		Pedido pedido = MAPPER.map(pedidoRequest, Pedido.class);
		pedido.setDataPedido(LocalDateTime.now());
		pedido.setStatusPedido(StatusPedidoEnum.RECEBIDO);

		valideCliente(pedido);

		valideProduto(pedido);

		//Salva o pedido e obtem seu numero
		pedido = pedidoJpaRepository.save(pedido);

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
