package br.com.postech.techchallenge.microservico.pedido.unit.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.postech.techchallenge.microservico.pedido.ObjectCreatorHelper;
import br.com.postech.techchallenge.microservico.pedido.comum.enums.CategoriaEnum;
import br.com.postech.techchallenge.microservico.pedido.entity.Produto;
import br.com.postech.techchallenge.microservico.pedido.repository.ProdutoJpaRepository;

class ProdutoJpaRepositoryTest {

	@Mock
	private ProdutoJpaRepository produtoJpaRepository;
	
	AutoCloseable openMocks;
	
	@BeforeEach
	void setUp() {
		openMocks = MockitoAnnotations.openMocks(this);
	}
	
	@AfterEach
	void close() throws Exception {
		openMocks.close();
	}
	
	@Test
	void devePertimirBuscarProdutoPorCategoria() {
		var produtoModel1 = ObjectCreatorHelper.obterProduto();
		produtoModel1.setId(1L);
		
		var produtoModel2 = ObjectCreatorHelper.obterProduto();
		produtoModel2.setId(2L);
		
		var produtos = Arrays.asList(produtoModel1, produtoModel2);
		
		when(produtoJpaRepository.findByCategoria(any())).thenReturn(produtos);
		
		var lista = produtoJpaRepository.findByCategoria(CategoriaEnum.BEBIDA);
		
		assertThat(lista).hasSize(2).containsExactlyInAnyOrder(produtoModel1, produtoModel2);
		
		verify(produtoJpaRepository, times(1)).findByCategoria(any());
	}

	@Test
	void devePertimirSalvarProduto() {
		var produtoModel = ObjectCreatorHelper.obterProduto();
		produtoModel.setId(1L);
		
		when(produtoJpaRepository.save(any())).thenReturn(produtoModel);
		
		var produto = produtoJpaRepository.save(produtoModel);
		
		assertThat(produto).isInstanceOf(Produto.class).isNotNull().isEqualTo(produtoModel);
		assertThat(produto).extracting(Produto::getId).isEqualTo(produtoModel.getId());
		assertThat(produto).extracting(Produto::getNome).isEqualTo(produtoModel.getNome());
		assertThat(produto).extracting(Produto::getDescricao).isEqualTo(produtoModel.getDescricao());
		assertThat(produto).extracting(Produto::getCategoria).isEqualTo(produtoModel.getCategoria());
		assertThat(produto).extracting(Produto::getValor).isEqualTo(produtoModel.getValor());
		
		verify(produtoJpaRepository, times(1)).save(any());
	}

	@Test
	void devePertimirDeletarProdutoPorId() {		
		doNothing().when(produtoJpaRepository).deleteById(anyLong());
		
		produtoJpaRepository.deleteById(1L);
		
		verify(produtoJpaRepository, times(1)).deleteById(anyLong());		
	}
}
