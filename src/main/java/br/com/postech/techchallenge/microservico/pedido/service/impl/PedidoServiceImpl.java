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

import br.com.postech.techchallenge.microservico.pedido.comum.converts.CategoriaParaInteiroConverter;
import br.com.postech.techchallenge.microservico.pedido.comum.converts.StatusPedidoParaInteiroConverter;
import br.com.postech.techchallenge.microservico.pedido.comum.enums.StatusPedidoEnum;
import br.com.postech.techchallenge.microservico.pedido.comum.util.CpfCnpjUtil;
import br.com.postech.techchallenge.microservico.pedido.configuration.ModelMapperConfiguration;
import br.com.postech.techchallenge.microservico.pedido.domain.PedidoDocumento;
import br.com.postech.techchallenge.microservico.pedido.entity.Cliente;
import br.com.postech.techchallenge.microservico.pedido.entity.Pedido;
import br.com.postech.techchallenge.microservico.pedido.entity.PedidoProduto;
import br.com.postech.techchallenge.microservico.pedido.entity.Produto;
import br.com.postech.techchallenge.microservico.pedido.exception.BusinessException;
import br.com.postech.techchallenge.microservico.pedido.model.request.PagamentoRequest;
import br.com.postech.techchallenge.microservico.pedido.model.request.PedidoRequest;
import br.com.postech.techchallenge.microservico.pedido.model.response.PagamentoResponse;
import br.com.postech.techchallenge.microservico.pedido.model.response.PedidoProdutoResponse;
import br.com.postech.techchallenge.microservico.pedido.model.response.PedidoResponse;
import br.com.postech.techchallenge.microservico.pedido.model.response.ProdutoResponse;
import br.com.postech.techchallenge.microservico.pedido.repository.ClienteJpaRepository;
import br.com.postech.techchallenge.microservico.pedido.repository.PedidoJpaRepository;
import br.com.postech.techchallenge.microservico.pedido.repository.PedidoMongoRepository;
import br.com.postech.techchallenge.microservico.pedido.repository.ProdutoJpaRepository;
import br.com.postech.techchallenge.microservico.pedido.service.PedidoService;
import br.com.postech.techchallenge.microservico.pedido.service.integracao.ApiMicroServicePagamento;
import lombok.RequiredArgsConstructor;


@Service
@Transactional
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
	private static final ModelMapper MAPPER = ModelMapperConfiguration.getModelMapper();

	private final PedidoJpaRepository pedidoJpaRepository;
	private final ClienteJpaRepository clienteJpaRepository;
	private final ProdutoJpaRepository produtoJpaRepository;
	private final PedidoMongoRepository pedidoMongoRepository;
	private final ApiMicroServicePagamento apiMicroServicePagamento;
	
	@Override
	public List<PedidoResponse> findTodosPedidosAtivos()throws BusinessException{
		List<Pedido> pedidos = pedidoJpaRepository
				.findByStatusPedidoNotIn(
						Arrays.asList(
								StatusPedidoEnum.PRONTO,
								StatusPedidoEnum.ENTREGUE)
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
		var pedidoDocumento = pedidoMongoRepository.findByNumeroPedido(id.longValue());
		if (!pedidoDocumento.isPresent()) {
			Pedido pedido = pedidoJpaRepository
					.findById(id)
					.orElseThrow(() -> new BusinessException("Pedido não encontrado!"));
			
			MAPPER.typeMap(Pedido.class, PedidoResponse.class)
				.addMappings(mapperA -> mapperA
						.using(new StatusPedidoParaInteiroConverter())
							.map(Pedido::getStatusPedido, PedidoResponse::setStatusPedido))
				.addMappings(mapperB -> {
					  mapperB.map(src -> src.getId(),PedidoResponse::setNumeroPedido);
			});
			return MAPPER.map(pedido, PedidoResponse.class);
		}
		
		return MAPPER.map(pedidoDocumento, PedidoResponse.class);
	}
	
	@Override
	public List<PedidoProdutoResponse> findProdutosByPedido(Integer numeroPedido) throws BusinessException {
		Pedido pedido = pedidoJpaRepository
				.findById(numeroPedido)
				.orElseThrow(() -> new BusinessException("Pedido não encontrado!"));	
		
		MAPPER.typeMap(Produto.class, ProdutoResponse.class)
		.addMappings(mapperA -> mapperA.using(new CategoriaParaInteiroConverter())
				.map(Produto::getCategoria, ProdutoResponse::setCategoria));
		
		return MAPPER.map(pedido.getProdutos(), new TypeToken<List<PedidoProdutoResponse>>() {}.getType());
	}

	@Override
	public PedidoResponse fazerPedidoFake(PedidoRequest pedidoRequest) throws BusinessException {
		//Obtem os dados do pedido
		Pedido pedido = MAPPER.map(pedidoRequest, Pedido.class);
		pedido.setStatusPedido(StatusPedidoEnum.get(pedidoRequest.statusPedido()));
		pedido.setDataPedido(LocalDateTime.now());
		pedido.setStatusPedido(StatusPedidoEnum.AGUARDANDO_PAGAMENTO);

		valideCliente(pedido);

		valideProduto(pedido);

		//Salva o pedido e obtem seu numero
		pedido = pedidoJpaRepository.save(pedido);
		
		//Obtem o valor total do pedido
		BigDecimal valorPedido = pedido.getProdutos()
				.stream()
				.map(PedidoProduto::total)
				.reduce((x, y) -> x.add(y))
				.orElse(BigDecimal.ZERO);
		
		//Cria um registro de pagamento no banco como Pendente
		PagamentoRequest pagamento = new PagamentoRequest(null, pedido.getId(), 1, valorPedido);
		
		PagamentoResponse response = apiMicroServicePagamento.criarPagamento(pagamento);

		MAPPER.typeMap(Pedido.class, PedidoResponse.class)
		.addMappings(mapperA -> mapperA
				.using(new StatusPedidoParaInteiroConverter())
					.map(Pedido::getStatusPedido, PedidoResponse::setStatusPedido))
		.addMappings(mapper -> {
			  mapper.map(src -> src.getId(),PedidoResponse::setNumeroPedido);
		});

		var pedidoResponse = MAPPER.map(pedido, PedidoResponse.class);
		
		pedidoResponse.setNumeroPagamento(response.getNumeroPagamento());
		pedidoResponse.setStatusPagamento(response.getStatusPagamento());
		pedidoResponse.setQrCodePix(response.getQrCodePix());
		
		var pedidoDocumento = MAPPER.map(pedidoResponse, PedidoDocumento.class);
		
		pedidoMongoRepository.save(pedidoDocumento);
		
		return pedidoResponse;
	}
	
	@Override
	public PedidoResponse atualizarPedido(PedidoRequest pedidoRequest) throws BusinessException {
		Pedido pedido = pedidoJpaRepository
				.findById(pedidoRequest.numeroPedido().intValue())
				.orElseThrow(() -> new BusinessException("Pedido não encontrado!"));	
		
		pedido.setStatusPedido(StatusPedidoEnum.get(pedidoRequest.statusPedido()));
		pedido = pedidoJpaRepository.save(pedido);
		
		MAPPER.typeMap(Pedido.class, PedidoResponse.class)
			.addMappings(mapperA -> mapperA
					.using(new StatusPedidoParaInteiroConverter())
						.map(Pedido::getStatusPedido, PedidoResponse::setStatusPedido))
			.addMappings(mapper -> {
				  mapper.map(src -> src.getId(),PedidoResponse::setNumeroPedido);
		});
		
		return updatePedidoDocumento(pedido, pedidoRequest); 
	}

	private void valideProduto(Pedido pedido)  throws BusinessException{
		//Verifica se o está cadastrado produtos
		Optional.ofNullable(pedido.getProdutos())
			.orElseThrow(() -> new BusinessException("É obrigatório informar algum produto!"))
			.stream()
			.filter(pedidoProduto -> Objects.nonNull(pedidoProduto.getProduto()) &&
								     Objects.nonNull(pedidoProduto.getProduto().getId()))
			.findAny()
			.orElseThrow(() -> new BusinessException("Produto não encontrado!"));

		//Atribui atualiza lista de pedido_produto.
		pedido.getProdutos()
			.stream()
			.distinct()
			.forEach(pedidoProduto -> {
					pedidoProduto.setPedido(pedido);
					produtoJpaRepository.findById(pedidoProduto.getProduto().getId())
		            .ifPresentOrElse(
		                produto -> pedidoProduto.setProduto(produto),
		                () -> {
		                    throw new BusinessException("Produto não cadastrado!");
		                }
		            );
			});
	}

	private void valideCliente(Pedido pedido) throws BusinessException{
		//Caso informe dados do cliente, é obrigatorio o cliente existir
		if(Objects.nonNull(pedido.getCliente())) {
			pedido.getCliente().setCpf(CpfCnpjUtil.removeMaskCPFCNPJ(pedido.getCliente().getCpf()));

			Cliente cliente = clienteJpaRepository.findByCpfOrNomeOrEmail(pedido.getCliente().getCpf(),
					pedido.getCliente().getNome(), pedido.getCliente().getEmail())
					.orElseThrow(() -> new BusinessException("Cliente não encontrado!"));

			pedido.setCliente(cliente);
		}
	}
	
	private PedidoResponse updatePedidoDocumento(Pedido pedido, PedidoRequest pedidoRequest) {
		var pedidoDoc = pedidoMongoRepository.findByNumeroPedido(pedidoRequest.numeroPedido());
		if(pedidoDoc.isPresent()) {
			var pedidoDocumento = pedidoDoc.get();
			pedidoDocumento.setStatusPedido(pedidoRequest.statusPedido());
			
			pedidoDocumento = pedidoMongoRepository.save(pedidoDocumento);
			
			return MAPPER.map(pedidoDocumento, PedidoResponse.class);
		}
		
		return MAPPER.map(pedido, PedidoResponse.class);
	}
}
