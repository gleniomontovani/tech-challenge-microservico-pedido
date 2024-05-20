package br.com.postech.techchallenge.microservico.pedido.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.postech.techchallenge.microservico.pedido.ObjectCreatorHelper;
import br.com.postech.techchallenge.microservico.pedido.comum.util.Constantes;
import br.com.postech.techchallenge.microservico.pedido.exception.BusinessException;
import br.com.postech.techchallenge.microservico.pedido.model.response.PedidoResponse;
import br.com.postech.techchallenge.microservico.pedido.repository.ClienteJpaRepository;
import br.com.postech.techchallenge.microservico.pedido.repository.PedidoJpaRepository;
import br.com.postech.techchallenge.microservico.pedido.repository.PedidoMongoRepository;
import br.com.postech.techchallenge.microservico.pedido.repository.ProdutoJpaRepository;
import br.com.postech.techchallenge.microservico.pedido.service.PedidoService;
import br.com.postech.techchallenge.microservico.pedido.service.impl.PedidoServiceImpl;
import br.com.postech.techchallenge.microservico.pedido.service.integracao.ApiMicroServicePagamento;

class PedidoServiceTest {
	
	private PedidoService pedidoService;
	@Mock
	private PedidoJpaRepository pedidoJpaRepository;
	@Mock
	private ClienteJpaRepository clienteJpaRepository;
	@Mock
	private ProdutoJpaRepository produtoJpaRepository;
	@Mock
	private PedidoMongoRepository pedidoMongoRepository;
	@Mock
	private ApiMicroServicePagamento pagamentoApiService;

	AutoCloseable openMocks;
	
	@BeforeEach
	void setUp() {
		openMocks = MockitoAnnotations.openMocks(this);
		pedidoService = new PedidoServiceImpl(pedidoJpaRepository, clienteJpaRepository, produtoJpaRepository, pedidoMongoRepository, pagamentoApiService);
	}
	
	@AfterEach
	void close() throws Exception {
		openMocks.close();
	}
		
	@Test
	void devePermitirBuscarTodosPedidosAtivos() {
		var pedidoModel1 = ObjectCreatorHelper.obterPedido();
		pedidoModel1.setId(1L);
		
		var pedidoModel2 = ObjectCreatorHelper.obterPedido();
		pedidoModel2.setId(2L);
		
		var pedidos = Arrays.asList(pedidoModel1, pedidoModel2);
		
		when(pedidoJpaRepository.findByStatusPedidoNotIn(anyList())).thenReturn(pedidos);
		
		var pedidoAtivos = pedidoService.findTodosPedidosAtivos();
		
		assertThat(pedidoAtivos).hasSize(2);
		assertThat(pedidoAtivos)
			.asList()
			.allSatisfy(pedido -> {
				assertThat(pedido).isNotNull();
				assertThat(pedido).isInstanceOf(PedidoResponse.class);
			});
		
		verify(pedidoJpaRepository, times(1)).findByStatusPedidoNotIn(anyList());
	}

	@Test
	void devePermitirBuscaPedidoPorId() throws Exception {
		var pedidoModel = ObjectCreatorHelper.obterPedido();
		pedidoModel.setId(1L);
		
		when(pedidoJpaRepository.findById(anyInt())).thenReturn(Optional.of(pedidoModel));
		
		var pedido = pedidoService.findById(1);
		
		assertThat(pedido).isNotNull().isInstanceOf(PedidoResponse.class);
		assertThat(pedido).extracting(PedidoResponse::getNumeroPedido).isEqualTo(pedidoModel.getId());
		assertThat(pedido.getCliente().getNumero()).isEqualTo(pedidoModel.getCliente().getId());
		assertThat(pedido.getCliente().getCpf()).isEqualTo(pedidoModel.getCliente().getCpf());
		assertThat(pedido).extracting(PedidoResponse::getDataPedido).isEqualTo(pedidoModel.getDataPedido().toString());
		assertThat(pedido).extracting(PedidoResponse::getStatusPedido).isEqualTo(pedidoModel.getStatusPedido().getValue());
		
		verify(pedidoJpaRepository, times(1)).findById(anyInt());
	}
	
	@Test
	void deveGerarExcecao_QuandoBuscarPor_IdNaoExistente() {
		assertThatThrownBy(
				() -> pedidoService.findById(1))
				.isInstanceOf(BusinessException.class)
				.hasMessage("Pedido não encontrado!");	
		
		verify(pedidoJpaRepository, times(1)).findById(anyInt());
	}

	@Test
	void devePermitirFazerPedidoFake() {
		var pedidoRequestModel = ObjectCreatorHelper.obterPedidoRequest();
		
		var pedidoModel = ObjectCreatorHelper.obterPedido();
		pedidoModel.setId(1L);
		
		var clienteModel = ObjectCreatorHelper.obterCliente();
		clienteModel.setId(1L);
		clienteModel.setCpf(Constantes.CLIENTE_CPF_1);
		
		var produtoModel = ObjectCreatorHelper.obterProduto();
		produtoModel.setId(1L);
		
		var pagamentoResponse = ObjectCreatorHelper.obterPagamentoResponse();
		
		when(clienteJpaRepository.findByCpfOrNomeOrEmail(anyString(), anyString(), anyString())).thenReturn(Optional.of(clienteModel));
		when(produtoJpaRepository.findById(anyLong())).thenReturn(Optional.of(produtoModel));
		when(pedidoJpaRepository.save(any())).thenReturn(pedidoModel);
		when(pagamentoApiService.criarPagamento(any())).thenReturn(pagamentoResponse);
		
		var pedido = pedidoService.fazerPedidoFake(pedidoRequestModel);
		
		assertThat(pedido).isNotNull().isInstanceOf(PedidoResponse.class);
		assertThat(pedido).extracting(PedidoResponse::getNumeroPedido).isEqualTo(pedidoModel.getId());
		assertThat(pedido.getCliente().getNumero()).isEqualTo(pedidoModel.getCliente().getId());
		assertThat(pedido.getCliente().getCpf()).isEqualTo(pedidoModel.getCliente().getCpf());
		assertThat(pedido).extracting(PedidoResponse::getDataPedido).isEqualTo(pedidoModel.getDataPedido().toString());
		assertThat(pedido).extracting(PedidoResponse::getStatusPedido).isEqualTo(pedidoModel.getStatusPedido().getValue());
		assertThat(pedido).extracting(PedidoResponse::getStatusPagamento).isNotNull();
		
		verify(clienteJpaRepository, times(1)).findByCpfOrNomeOrEmail(anyString(), anyString(), anyString());
		verify(produtoJpaRepository, times(1)).findById(anyLong());
		verify(pedidoJpaRepository, times(1)).save(any());
		verify(pagamentoApiService, times(1)).criarPagamento(any());
	}
	
	@Test
	void devePerimitirAtualizarPedido() {
		var pedidoRequestModel = ObjectCreatorHelper.obterPedidoRequest();
		
		var pedidoModel = ObjectCreatorHelper.obterPedido();
		pedidoModel.setId(1L);
		
		when(pedidoJpaRepository.findById(anyInt())).thenReturn(Optional.of(pedidoModel));
		when(pedidoJpaRepository.save(any())).thenReturn(pedidoModel);
		
		var pedido = pedidoService.atualizarPedido(pedidoRequestModel);
		
		assertThat(pedido).isNotNull().isInstanceOf(PedidoResponse.class);
		assertThat(pedido).extracting(PedidoResponse::getNumeroPedido).isEqualTo(pedidoModel.getId());
		assertThat(pedido.getCliente().getNumero()).isEqualTo(pedidoModel.getCliente().getId());
		assertThat(pedido.getCliente().getCpf()).isEqualTo(pedidoModel.getCliente().getCpf());
		assertThat(pedido).extracting(PedidoResponse::getDataPedido).isEqualTo(pedidoModel.getDataPedido().toString());
		assertThat(pedido).extracting(PedidoResponse::getStatusPedido).isEqualTo(pedidoModel.getStatusPedido().getValue());
		
		verify(pedidoJpaRepository, times(1)).findById(anyInt());
		verify(pedidoJpaRepository, times(1)).save(any());
	}
	
	@Test
	void deveGerarExcecao_QuandoFizerPedidoFakePara_ClienteQueNaoExistente() {		
		var pedidoRequestModel = ObjectCreatorHelper.obterPedidoRequest();
		
		assertThatThrownBy(
				() -> pedidoService.fazerPedidoFake(pedidoRequestModel))
				.isInstanceOf(BusinessException.class)
				.hasMessage("Cliente não encontrado!");	
		
		verify(clienteJpaRepository, times(1)).findByCpfOrNomeOrEmail(anyString(), anyString(), anyString());
	}
	
	@Test
	void deveGerarExcecao_QuandoFizerPedidoFakePara_ProdutoQueNaoExistente() {		
		var pedidoRequestModel = ObjectCreatorHelper.obterPedidoRequestProdutoNulo();
		var clienteModel = ObjectCreatorHelper.obterCliente();
		clienteModel.setId(1L);
		clienteModel.setCpf(Constantes.CLIENTE_CPF_1);
		
		when(clienteJpaRepository.findByCpfOrNomeOrEmail(anyString(), anyString(), anyString())).thenReturn(Optional.of(clienteModel));
		
		assertThatThrownBy(
				() -> pedidoService.fazerPedidoFake(pedidoRequestModel))
				.isInstanceOf(BusinessException.class)
				.hasMessage("É obrigatório informar algum produto!");	
		
		verify(clienteJpaRepository, times(1)).findByCpfOrNomeOrEmail(anyString(), anyString(), anyString());
	}
	
	@Test
	void deveGerarExcecao_QuandoFizerPedidoFakePara_ProdutoNaoCadastrado() {		
		var pedidoRequestModel = ObjectCreatorHelper.obterPedidoRequestSemProduto();
		var clienteModel = ObjectCreatorHelper.obterCliente();
		clienteModel.setId(1L);
		clienteModel.setCpf(Constantes.CLIENTE_CPF_1);
		
		when(clienteJpaRepository.findByCpfOrNomeOrEmail(anyString(), anyString(), anyString())).thenReturn(Optional.of(clienteModel));
		
		assertThatThrownBy(
				() -> pedidoService.fazerPedidoFake(pedidoRequestModel))
				.isInstanceOf(BusinessException.class)
				.hasMessage("Produto não encontrado!");	
		
		verify(clienteJpaRepository, times(1)).findByCpfOrNomeOrEmail(anyString(), anyString(), anyString());
	}
}
