package br.com.postech.techchallenge.microservico.pedido.unit.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import br.com.postech.techchallenge.microservico.pedido.controller.ProdutoController;
import br.com.postech.techchallenge.microservico.pedido.handler.RestHandlerException;
import br.com.postech.techchallenge.microservico.pedido.model.request.ProdutoRequest;
import br.com.postech.techchallenge.microservico.pedido.model.response.ProdutoResponse;
import br.com.postech.techchallenge.microservico.pedido.service.ProdutoService;

class ProdutoControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private ProdutoService produtoService;
	
	AutoCloseable openMocks;

	@BeforeEach
	void setUp() {
		openMocks = MockitoAnnotations.openMocks(this);
		ProdutoController produtoController = new ProdutoController(produtoService);
		mockMvc = MockMvcBuilders.standaloneSetup(produtoController)
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
	void devePermitirSalvarProduto() throws Exception {
		var produtoResponse = ObjectCreatorHelper.obterProdutoResponse();
		produtoResponse.setId(1L);
		
		var produtoRequest = ObjectCreatorHelper.obterProdutoRequest();
		
		when(produtoService.save(any(ProdutoRequest.class))).thenReturn(produtoResponse);
		
		mockMvc.perform(post("/v1/produtos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(Utilitario.asJsonString(produtoRequest)))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.nome").value(produtoResponse.getNome()))
        .andExpect(jsonPath("$.descricao").value(produtoResponse.getDescricao()))
        .andExpect(jsonPath("$.categoria").value(produtoResponse.getCategoria()))
        .andExpect(jsonPath("$.valor").value(produtoResponse.getValor()));
		
		verify(produtoService, times(1)).save(any(ProdutoRequest.class));
	}

	@Test
	void devePerimitirDeletarProdutoPorId() throws Exception {
		
		doNothing().when(produtoService).deleteById(anyLong());
		
		mockMvc.perform(delete("/v1/produtos/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
		
		verify(produtoService, times(1)).deleteById(anyLong());
	}

	@Test
	void devePermitirAtualizarProduto() throws Exception {
		var produtoResponse = ObjectCreatorHelper.obterProdutoResponse();
		produtoResponse.setId(1L);
		
		var produtoRequest = ObjectCreatorHelper.obterProdutoRequest();
		
		when(produtoService.atualizar(anyLong(), any(ProdutoRequest.class))).thenReturn(produtoResponse);
		
		mockMvc.perform(put("/v1/produtos/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(Utilitario.asJsonString(produtoRequest)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.nome").value(produtoResponse.getNome()))
        .andExpect(jsonPath("$.descricao").value(produtoResponse.getDescricao()))
        .andExpect(jsonPath("$.categoria").value(produtoResponse.getCategoria()))
        .andExpect(jsonPath("$.valor").value(produtoResponse.getValor()));
		
		verify(produtoService, times(1)).atualizar(anyLong(), any(ProdutoRequest.class));
	}

	@Test
	void devePermitirBuscarProdutoPorId() throws Exception {
		var produtoResponse = ObjectCreatorHelper.obterProdutoResponse();
		produtoResponse.setId(1L);
		
		when(produtoService.findById(anyLong())).thenReturn(produtoResponse);
		
		mockMvc.perform(get("/v1/produtos/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.nome").value(produtoResponse.getNome()))
        .andExpect(jsonPath("$.descricao").value(produtoResponse.getDescricao()))
        .andExpect(jsonPath("$.categoria").value(produtoResponse.getCategoria()))
        .andExpect(jsonPath("$.valor").value(produtoResponse.getValor()));
		
		verify(produtoService, times(1)).findById(anyLong());
	}

	@Test
	void devePerimitrListarProdutosPorCategoria() throws Exception {
		var produtoResponse1 = ObjectCreatorHelper.obterProdutoResponse();
		produtoResponse1.setId(1L);
		
		var produtoResponse2 = ObjectCreatorHelper.obterProdutoResponse();
		produtoResponse2.setId(2L);
		
		List<ProdutoResponse> produtosResponse = Arrays.asList(produtoResponse1, produtoResponse2);
		
		when(produtoService.findByCategoria(anyInt())).thenReturn(produtosResponse);
		
		mockMvc.perform(get("/v1/produtos?categoria=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.[0].nome").value(produtoResponse1.getNome()))
        .andExpect(jsonPath("$.[0].descricao").value(produtoResponse1.getDescricao()))
        .andExpect(jsonPath("$.[0].categoria").value(produtoResponse1.getCategoria()))
        .andExpect(jsonPath("$.[0].valor").value(produtoResponse1.getValor()));
		
		verify(produtoService, times(1)).findByCategoria(anyInt());
	}
}
