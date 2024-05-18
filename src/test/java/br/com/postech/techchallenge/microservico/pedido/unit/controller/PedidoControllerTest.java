package br.com.postech.techchallenge.microservico.pedido.unit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.postech.techchallenge.microservico.pedido.ObjectCreatorHelper;
import br.com.postech.techchallenge.microservico.pedido.comum.util.Constantes;
import br.com.postech.techchallenge.microservico.pedido.comum.util.Utilitario;
import br.com.postech.techchallenge.microservico.pedido.controller.PedidoController;
import br.com.postech.techchallenge.microservico.pedido.handler.RestHandlerException;
import br.com.postech.techchallenge.microservico.pedido.model.request.PedidoRequest;
import br.com.postech.techchallenge.microservico.pedido.model.response.PedidoResponse;
import br.com.postech.techchallenge.microservico.pedido.service.PedidoService;

class PedidoControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private PedidoService pedidoService;
	
	AutoCloseable openMocks;

	@BeforeEach
	void setUp() {
		openMocks = MockitoAnnotations.openMocks(this);
		PedidoController pedidoController = new PedidoController(pedidoService);
		mockMvc = MockMvcBuilders.standaloneSetup(pedidoController)
		        .setControllerAdvice(new RestHandlerException())
		        .addFilter((request, response, chain) -> {
		          response.setCharacterEncoding(Constantes.UTF_8);
		          chain.doFilter(request, response);
		        }, "/*")
		        .build();
	}
	
	@AfterEach
	void close() throws Exception {
		openMocks.close();
	}

	@Test
	void devePermitirListarTodosPedidosAtivos() throws Exception {
		var pedidoResponse1 = ObjectCreatorHelper.obterPedidoResponse();
		pedidoResponse1.setNumeroPedido(1L);
		
		var pedidoResponse2 = ObjectCreatorHelper.obterPedidoResponse();
		pedidoResponse2.setNumeroPedido(2L);
		
		List<PedidoResponse> pedidosResponse = Arrays.asList(pedidoResponse1, pedidoResponse2);
		
		when(pedidoService.findTodosPedidosAtivos()).thenReturn(pedidosResponse);
		
		mockMvc.perform(get("/v1/pedidos")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.[0].numeroPedido").value(pedidoResponse1.getNumeroPedido()))
        .andExpect(jsonPath("$.[0].cliente").exists())
        .andExpect(jsonPath("$.[0].dataPedido").value(pedidoResponse1.getDataPedido()))
        .andExpect(jsonPath("$.[0].statusPedido").value(pedidoResponse1.getStatusPedido()))
        .andExpect(jsonPath("$.[0].statusPagamento").value(pedidoResponse1.getStatusPagamento()));
		
		verify(pedidoService, times(1)).findTodosPedidosAtivos();
	}

	@Test
	void devePermitirBuscarPedidoPorId() throws Exception {
		var pedidoResponse = ObjectCreatorHelper.obterPedidoResponse();
		pedidoResponse.setNumeroPedido(1L);
		
		when(pedidoService.findById(anyInt())).thenReturn(pedidoResponse);
		
		mockMvc.perform(get("/v1/pedidos/{idPedido}", 1)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.numeroPedido").value(pedidoResponse.getNumeroPedido()))
        .andExpect(jsonPath("$.cliente").exists())
        .andExpect(jsonPath("$.dataPedido").value(pedidoResponse.getDataPedido()))
        .andExpect(jsonPath("$.statusPedido").value(pedidoResponse.getStatusPedido()))
        .andExpect(jsonPath("$.statusPagamento").value(pedidoResponse.getStatusPagamento()));
		
		verify(pedidoService, times(1)).findById(anyInt());
	}

	@Test
	void devePermitirFazerCheckoutFake() throws Exception {
		var pedidoRequest = ObjectCreatorHelper.obterPedidoRequest();
		
		var pedidoResponse = ObjectCreatorHelper.obterPedidoResponse();
		pedidoResponse.setNumeroPedido(1L);
		
		when(pedidoService.fazerPedidoFake(any(PedidoRequest.class))).thenReturn(pedidoResponse);
		
		mockMvc.perform(post("/v1/pedidos/checkout")
				.contentType(MediaType.APPLICATION_JSON)
				.content(Utilitario.asJsonString(pedidoRequest)))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.numeroPedido").value(pedidoResponse.getNumeroPedido()))
        .andExpect(jsonPath("$.cliente").exists())
        .andExpect(jsonPath("$.dataPedido").value(pedidoResponse.getDataPedido()))
        .andExpect(jsonPath("$.statusPedido").value(pedidoResponse.getStatusPedido()))
        .andExpect(jsonPath("$.statusPagamento").value(pedidoResponse.getStatusPagamento()));
		
		verify(pedidoService, times(1)).fazerPedidoFake(any(PedidoRequest.class));
	}
	
	@Test
	void devePermitirAtualizarPedido() throws Exception {
		var pedidoRequest = ObjectCreatorHelper.obterPedidoRequest();
		
		var pedidoResponse = ObjectCreatorHelper.obterPedidoResponse();
		pedidoResponse.setNumeroPedido(1L);
		
		when(pedidoService.atualizarPedido(any(PedidoRequest.class))).thenReturn(pedidoResponse);
		
		mockMvc.perform(put("/v1/pedidos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(Utilitario.asJsonString(pedidoRequest)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.numeroPedido").value(pedidoResponse.getNumeroPedido()))
        .andExpect(jsonPath("$.cliente").exists())
        .andExpect(jsonPath("$.dataPedido").value(pedidoResponse.getDataPedido()))
        .andExpect(jsonPath("$.statusPedido").value(pedidoResponse.getStatusPedido()));
		
		verify(pedidoService, times(1)).atualizarPedido(any(PedidoRequest.class));
	}
}
