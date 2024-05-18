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
import br.com.postech.techchallenge.microservico.pedido.controller.ClienteController;
import br.com.postech.techchallenge.microservico.pedido.handler.RestHandlerException;
import br.com.postech.techchallenge.microservico.pedido.model.request.ClienteRequest;
import br.com.postech.techchallenge.microservico.pedido.model.response.ClienteResponse;
import br.com.postech.techchallenge.microservico.pedido.service.ClienteService;

class ClienteControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private ClienteService clienteService;
	
	AutoCloseable openMocks;

	@BeforeEach
	void setUp() {
		openMocks = MockitoAnnotations.openMocks(this);
		ClienteController clienteController = new ClienteController(clienteService);
		mockMvc = MockMvcBuilders.standaloneSetup(clienteController)
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
	void devePermitirListarClientesAtivos() throws Exception {
		var clienteResponse1 = ObjectCreatorHelper.obterClienteResponse();
		clienteResponse1.setNumero(1L);
		clienteResponse1.setCpf(Constantes.CLIENTE_CPF_1);
		
		var clienteResponse2 = ObjectCreatorHelper.obterClienteResponse();
		clienteResponse2.setNumero(2L);
		clienteResponse2.setCpf(Constantes.CLIENTE_CPF_2);
		
		List<ClienteResponse> clientesResponse = Arrays.asList(clienteResponse1, clienteResponse2);
		
		when(clienteService.listarClientesAtivos()).thenReturn(clientesResponse);
		
		mockMvc.perform(get("/v1/clientes")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.[0].numero").value(clienteResponse1.getNumero()))
        .andExpect(jsonPath("$.[0].nome").value(clienteResponse1.getNome()))
        .andExpect(jsonPath("$.[0].status").value(clienteResponse1.getStatus()))
        .andExpect(jsonPath("$.[0].cpf").value(clienteResponse1.getCpf()))
        .andExpect(jsonPath("$.[0].email").value(clienteResponse1.getEmail()));
		
		verify(clienteService, times(1)).listarClientesAtivos();
	}

	@Test
	void devePermitirBuscarCliente() throws Exception {
		var clienteResponse = ObjectCreatorHelper.obterClienteResponse();
		clienteResponse.setNumero(1L);
		clienteResponse.setCpf(Constantes.CLIENTE_CPF_1);
		
		when(clienteService.findById(anyInt())).thenReturn(clienteResponse);
		
		mockMvc.perform(get("/v1/clientes/{idCliente}", 1)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.numero").value(clienteResponse.getNumero()))
        .andExpect(jsonPath("$.nome").value(clienteResponse.getNome()))
        .andExpect(jsonPath("$.status").value(clienteResponse.getStatus()))
        .andExpect(jsonPath("$.cpf").value(clienteResponse.getCpf()))
        .andExpect(jsonPath("$.email").value(clienteResponse.getEmail()));
		
		verify(clienteService, times(1)).findById(anyInt());
	}

	@Test
	void devePermitirSalvarCliente() throws Exception {
		var clienteRequest = ObjectCreatorHelper.obterClienteRequest();
		
		var clienteResponse = ObjectCreatorHelper.obterClienteResponse();
		clienteResponse.setNumero(1L);
		clienteResponse.setCpf(Constantes.CLIENTE_CPF_1);
		
		when(clienteService.save(any(ClienteRequest.class))).thenReturn(clienteResponse);

		mockMvc.perform(post("/v1/clientes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(Utilitario.asJsonString(clienteRequest)))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.numero").value(clienteResponse.getNumero()))
        .andExpect(jsonPath("$.nome").value(clienteResponse.getNome()))
        .andExpect(jsonPath("$.status").value(clienteResponse.getStatus()))
        .andExpect(jsonPath("$.cpf").value(clienteResponse.getCpf()))
        .andExpect(jsonPath("$.email").value(clienteResponse.getEmail()));
		
		verify(clienteService, times(1)).save(any(ClienteRequest.class));
	}

	@Test
	void devePermitirAtualizarCliente() throws Exception {
		var clienteRequest = ObjectCreatorHelper.obterClienteRequest();
		
		var clienteResponse = ObjectCreatorHelper.obterClienteResponse();
		clienteResponse.setNumero(1L);
		clienteResponse.setCpf(Constantes.CLIENTE_CPF_1);
		
		when(clienteService.atualizarCliente(anyInt(), any(ClienteRequest.class))).thenReturn(clienteResponse);

		mockMvc.perform(put("/v1/clientes/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON)
				.content(Utilitario.asJsonString(clienteRequest)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.numero").value(clienteResponse.getNumero()))
        .andExpect(jsonPath("$.nome").value(clienteResponse.getNome()))
        .andExpect(jsonPath("$.status").value(clienteResponse.getStatus()))
        .andExpect(jsonPath("$.cpf").value(clienteResponse.getCpf()))
        .andExpect(jsonPath("$.email").value(clienteResponse.getEmail()));
		
		verify(clienteService, times(1)).atualizarCliente(anyInt(), any(ClienteRequest.class));
	}

	@Test
	void devePermitirDesativarCliente() throws Exception {
		var clienteRequest = ObjectCreatorHelper.obterClienteRequest();
		
		var clienteResponse = ObjectCreatorHelper.obterClienteResponse();
		clienteResponse.setNumero(1L);
		clienteResponse.setCpf(Constantes.CLIENTE_CPF_1);
		
		when(clienteService.desativarCliente(anyInt())).thenReturn(clienteResponse);

		mockMvc.perform(put("/v1/clientes/desativar/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON)
				.content(Utilitario.asJsonString(clienteRequest)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.numero").value(clienteResponse.getNumero()))
        .andExpect(jsonPath("$.nome").value(clienteResponse.getNome()))
        .andExpect(jsonPath("$.status").value(clienteResponse.getStatus()))
        .andExpect(jsonPath("$.cpf").value(clienteResponse.getCpf()))
        .andExpect(jsonPath("$.email").value(clienteResponse.getEmail()));
		
		verify(clienteService, times(1)).desativarCliente(anyInt());
	}
}
