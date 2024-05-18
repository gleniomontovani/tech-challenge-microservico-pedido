package br.com.postech.techchallenge.microservico.pedido.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
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
import br.com.postech.techchallenge.microservico.pedido.exception.BusinessException;
import br.com.postech.techchallenge.microservico.pedido.model.response.ProdutoResponse;
import br.com.postech.techchallenge.microservico.pedido.repository.ProdutoJpaRepository;
import br.com.postech.techchallenge.microservico.pedido.service.ProdutoService;
import br.com.postech.techchallenge.microservico.pedido.service.impl.ProdutoServiceImpl;

class ProdutoServiceTest {
	
	private ProdutoService produtoService;
	@Mock
	private ProdutoJpaRepository produtoJpaRepository;
	
	AutoCloseable openMocks;
	
	@BeforeEach
	void setUp() {
		openMocks = MockitoAnnotations.openMocks(this);
		produtoService = new ProdutoServiceImpl(produtoJpaRepository);
	}
	
	@AfterEach
	void close() throws Exception {
		openMocks.close();
	}

	@Test
	void devePermitirBuscarProdutoPorCategoria() {
		var produtoModel1 = ObjectCreatorHelper.obterProduto();
		produtoModel1.setId(1L);
		
		var produtoModel2 = ObjectCreatorHelper.obterProduto();
		produtoModel2.setId(2L);
		
		var produtos = Arrays.asList(produtoModel1, produtoModel2);
		
		when(produtoJpaRepository.findByCategoria(any())).thenReturn(produtos);
		
		var produtosResponse = produtoService.findByCategoria(1);
		
		assertThat(produtosResponse).hasSize(2);
		
		assertThat(produtosResponse)
			.asList()
			.allSatisfy(produto -> {
				assertThat(produto).isNotNull();
				assertThat(produto).isInstanceOf(ProdutoResponse.class);
			});
		
		verify(produtoJpaRepository, times(1)).findByCategoria(any());		
	}

	@Test
	void devePermitirBuscarProdutoPorId() {
		var produtoModel = ObjectCreatorHelper.obterProduto();
		produtoModel.setId(1L);
		
		when(produtoJpaRepository.findById(anyLong())).thenReturn(Optional.of(produtoModel));
		
		var produtoResponse = produtoService.findById(1L);
		
		assertThat(produtoResponse).isInstanceOf(ProdutoResponse.class).isNotNull();
		assertThat(produtoResponse).extracting(ProdutoResponse::getId).isEqualTo(produtoModel.getId());
		assertThat(produtoResponse).extracting(ProdutoResponse::getNome).isEqualTo(produtoModel.getNome());
		assertThat(produtoResponse).extracting(ProdutoResponse::getDescricao).isEqualTo(produtoModel.getDescricao());
		assertThat(produtoResponse).extracting(ProdutoResponse::getValor).isEqualTo(produtoModel.getValor());
		assertThat(produtoResponse).extracting(ProdutoResponse::getCategoria).isEqualTo(produtoModel.getCategoria().getValue());
		
		verify(produtoJpaRepository, times(1)).findById(anyLong());
	}

	@Test
	void deveGerarExcecao_QuandoBuscarPorId_IdNaoExistir() {	
		assertThatThrownBy(
				() -> produtoService.findById(1L))
				.isInstanceOf(BusinessException.class)
				.hasMessage("Produto não encontrado!");	
		
		verify(produtoJpaRepository, times(1)).findById(anyLong());
	}
	
	@Test
	void devePermitirSalvaProduto() {
		var produtoModel = ObjectCreatorHelper.obterProduto();
		produtoModel.setId(1L);
		
		var produtoRequest = ObjectCreatorHelper.obterProdutoRequest();		
		
		when(produtoJpaRepository.save(any())).thenReturn(produtoModel);
		
		var produto = produtoService.save(produtoRequest);
		
		assertThat(produto).isInstanceOf(ProdutoResponse.class).isNotNull();
		assertThat(produto).extracting(ProdutoResponse::getId).isEqualTo(produtoModel.getId());
		assertThat(produto).extracting(ProdutoResponse::getNome).isEqualTo(produtoModel.getNome());
		assertThat(produto).extracting(ProdutoResponse::getDescricao).isEqualTo(produtoModel.getDescricao());
		assertThat(produto).extracting(ProdutoResponse::getCategoria).isEqualTo(produtoModel.getCategoria().getValue());
		assertThat(produto).extracting(ProdutoResponse::getValor).isEqualTo(produtoModel.getValor());
				
		verify(produtoJpaRepository, times(1)).save(any());
	}
	
	@Test
	void deveGerarExcecao_QuandoTentarSalvar_NaoContemImagemProduto() {
		var produtoRequest = ObjectCreatorHelper.obterProdutoRequestSemImagens();
		
		assertThatThrownBy(
				() -> produtoService.save(produtoRequest))
				.isInstanceOf(BusinessException.class)
				.hasMessage("É obrigatório informar pelo menos uma imgem para o produto!");	
	}

	@Test
	void devePermitirAtualizarProduto() {
		var produtoModel = ObjectCreatorHelper.obterProduto();
		produtoModel.setId(1L);
		
		var produtoRequest = ObjectCreatorHelper.obterProdutoRequest();
		
		when(produtoJpaRepository.findById(anyLong())).thenReturn(Optional.of(produtoModel));
		when(produtoJpaRepository.save(any())).thenReturn(produtoModel);
		
		var produto = produtoService.atualizar(1L, produtoRequest);
		
		assertThat(produto).isInstanceOf(ProdutoResponse.class).isNotNull();
		assertThat(produto).extracting(ProdutoResponse::getId).isEqualTo(produtoModel.getId());
		assertThat(produto).extracting(ProdutoResponse::getNome).isEqualTo(produtoModel.getNome());
		assertThat(produto).extracting(ProdutoResponse::getDescricao).isEqualTo(produtoModel.getDescricao());
		assertThat(produto).extracting(ProdutoResponse::getCategoria).isEqualTo(produtoModel.getCategoria().getValue());
		assertThat(produto).extracting(ProdutoResponse::getValor).isEqualTo(produtoModel.getValor());
		
		verify(produtoJpaRepository, times(1)).findById(anyLong());
		verify(produtoJpaRepository, times(1)).save(any());
	}

	@Test
	void testDeleteById() {
		var produtoModel = ObjectCreatorHelper.obterProduto();
		produtoModel.setId(1L);
		
		when(produtoJpaRepository.findById(anyLong())).thenReturn(Optional.of(produtoModel));
		doNothing().when(produtoJpaRepository).deleteById(anyLong());
		
		produtoService.deleteById(1L);
		
		verify(produtoJpaRepository, times(1)).findById(anyLong());
		verify(produtoJpaRepository, times(1)).deleteById(anyLong());
	}
}
