package br.com.postech.techchallenge.microservico.pedido.unit.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
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
import br.com.postech.techchallenge.microservico.pedido.comum.enums.StatusPedidoEnum;
import br.com.postech.techchallenge.microservico.pedido.entity.Pedido;
import br.com.postech.techchallenge.microservico.pedido.repository.PedidoJpaRepository;

class PedidoJpaRepositoryTest {

	@Mock
	private PedidoJpaRepository pedidoJpaRepository;
	
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
	void devePermitirBuscarPedidoPorStatusPedidoDiferenteDe() {
		var pedidoModel1 = ObjectCreatorHelper.obterPedido();
		pedidoModel1.setId(1L);

		var pedidoModel2 = ObjectCreatorHelper.obterPedido();
		pedidoModel2.setId(2L);

		var pedidos = Arrays.asList(pedidoModel1, pedidoModel2);

		when(pedidoJpaRepository.findByStatusPedidoNotIn(anyList())).thenReturn(pedidos);

		var lista = pedidoJpaRepository
				.findByStatusPedidoNotIn(Arrays.asList(StatusPedidoEnum.PRONTO, StatusPedidoEnum.ENTREGUE));
		
		assertThat(lista).hasSize(2).containsExactlyInAnyOrder(pedidoModel1, pedidoModel2);
		
		verify(pedidoJpaRepository, times(1)).findByStatusPedidoNotIn(anyList());
	}

	@Test
	void devePermitirSalvaPedido() {
		var pedidoModel = ObjectCreatorHelper.obterPedido();
		pedidoModel.setId(1L);
		
		when(pedidoJpaRepository.save(any())).thenReturn(pedidoModel);
		
		var pedido = pedidoJpaRepository.save(pedidoModel);
		
		assertThat(pedido).isInstanceOf(Pedido.class).isNotNull().isEqualTo(pedidoModel);
		assertThat(pedido).extracting(Pedido::getId).isEqualTo(pedidoModel.getId());
		assertThat(pedido).extracting(Pedido::getCliente).isEqualTo(pedidoModel.getCliente());
		assertThat(pedido).extracting(Pedido::getDataPedido).isEqualTo(pedidoModel.getDataPedido());
		assertThat(pedido).extracting(Pedido::getStatusPedido).isEqualTo(pedidoModel.getStatusPedido());
		
		verify(pedidoJpaRepository, times(1)).save(any());
	}
}
