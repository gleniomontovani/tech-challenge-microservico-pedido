package br.com.postech.techchallenge.microservico.pedido.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.postech.techchallenge.microservico.pedido.ObjectCreatorHelper;
import br.com.postech.techchallenge.microservico.pedido.comum.util.Constantes;
import br.com.postech.techchallenge.microservico.pedido.entity.Cliente;
import br.com.postech.techchallenge.microservico.pedido.model.response.ClienteResponse;
import br.com.postech.techchallenge.microservico.pedido.repository.ClienteJpaRepository;
import br.com.postech.techchallenge.microservico.pedido.service.ClienteService;
import br.com.postech.techchallenge.microservico.pedido.service.impl.ClienteServiceImpl;

class ClienteServiceTest {
	
	private ClienteService clienteService;
	@Mock
	private ClienteJpaRepository clienteJpaRepository;
	
	AutoCloseable openMocks;
	
	@BeforeEach
	void setUp() {
		openMocks = MockitoAnnotations.openMocks(this);
		clienteService = new ClienteServiceImpl(clienteJpaRepository);
	}
	
	@AfterEach
	void cloes() throws Exception {
		openMocks.close();
	}

	@Test
	void devePermitirListarClientesAtivos() {
		var clienteModel1 = ObjectCreatorHelper.obterCliente();
		clienteModel1.setId(1L);
		clienteModel1.setCpf(Constantes.CLIENTE_CPF_1);
		
		var clienteModel2 = ObjectCreatorHelper.obterCliente();
		clienteModel2.setId(2L);
		clienteModel2.setCpf(Constantes.CLIENTE_CPF_2);
		
		List<Cliente> clientesResponse = Arrays.asList(clienteModel1, clienteModel2);
		
		when(clienteJpaRepository.findByStatus(anyBoolean())).thenReturn(clientesResponse);
		
		var clientesAtivos = clienteService.listarClientesAtivos();
		
		assertThat(clientesAtivos).hasSize(2);
		assertThat(clientesAtivos)
			.asList()
			.allSatisfy(cliente -> {
				assertThat(cliente).isNotNull();
				assertThat(cliente).isInstanceOf(ClienteResponse.class);
			});
		
		verify(clienteJpaRepository, times(1)).findByStatus(anyBoolean());
	}

	@Test
	void devePermitirBuscarClientePorId() {
		var clienteModel = ObjectCreatorHelper.obterCliente();
		clienteModel.setId(1L);
		clienteModel.setCpf(Constantes.CLIENTE_CPF_1);
		
		when(clienteJpaRepository.findByIdAndStatus(anyInt(), anyBoolean())).thenReturn(Optional.of(clienteModel));
		
		var clienteResponse = clienteService.findById(1);
		
		assertThat(clienteResponse).isInstanceOf(ClienteResponse.class).isNotNull();
		assertThat(clienteResponse.getNumero()).isEqualTo(clienteModel.getId());
		assertThat(clienteResponse.getCpf()).isEqualTo(clienteModel.getCpf());
		assertThat(clienteResponse.getNome()).isEqualTo(clienteModel.getNome());
		assertThat(clienteResponse.getEmail()).isEqualTo(clienteModel.getEmail());
		assertThat(clienteResponse.getStatus()).isEqualTo(clienteModel.isStatus());
		
		verify(clienteJpaRepository, times(1)).findByIdAndStatus(anyInt(), anyBoolean());		
	}

	@Test
	void devePermitirSalvarCliente() {
		var clienteRequest = ObjectCreatorHelper.obterClienteRequest();
		
		when(clienteJpaRepository.save(any())).thenAnswer(p -> p.getArgument(0));
		
		var cliente = clienteService.save(clienteRequest);
		
		assertThat(cliente).isInstanceOf(ClienteResponse.class).isNotNull();
		assertThat(cliente.getNome()).isEqualTo(clienteRequest.nome());
		assertThat(cliente.getCpf()).isEqualTo(clienteRequest.cpf());
		assertThat(cliente.getEmail()).isEqualTo(clienteRequest.email());
		
		verify(clienteJpaRepository, times(1)).save(any());
	}

	@Test
	void devePermitirAtualizarCliente() {
		var clienteRequest = ObjectCreatorHelper.obterClienteRequest();
		var clienteModel = ObjectCreatorHelper.obterCliente();
		clienteModel.setId(1L);
		clienteModel.setCpf(Constantes.CLIENTE_CPF_1);
		
		when(clienteJpaRepository.findById(anyInt())).thenReturn(Optional.of(clienteModel));
		when(clienteJpaRepository.save(any())).thenReturn(clienteModel);
		
		var cliente = clienteService.atualizarCliente(1, clienteRequest);
		
		assertThat(cliente).isInstanceOf(ClienteResponse.class).isNotNull();
		assertThat(cliente.getNome()).isEqualTo(clienteRequest.nome());
		assertThat(cliente.getCpf()).isEqualTo(clienteRequest.cpf());
		assertThat(cliente.getEmail()).isEqualTo(clienteRequest.email());
		
		verify(clienteJpaRepository, times(1)).findById(anyInt());
		verify(clienteJpaRepository, times(1)).save(any());
	}

	@Test
	void devePermitirDesativarCliente() {
		var clienteRequest = ObjectCreatorHelper.obterClienteRequest();
		var clienteModel = ObjectCreatorHelper.obterCliente();
		clienteModel.setId(1L);
		clienteModel.setCpf(Constantes.CLIENTE_CPF_1);
		
		when(clienteJpaRepository.findById(anyInt())).thenReturn(Optional.of(clienteModel));
		when(clienteJpaRepository.save(any())).thenReturn(clienteModel);
		
		var cliente = clienteService.desativarCliente(1);
		
		assertThat(cliente).isInstanceOf(ClienteResponse.class).isNotNull();
		assertThat(cliente.getNome()).isEqualTo(clienteRequest.nome());
		assertThat(cliente.getCpf()).isEqualTo(clienteRequest.cpf());
		assertThat(cliente.getEmail()).isEqualTo(clienteRequest.email());
		
		verify(clienteJpaRepository, times(1)).findById(anyInt());
		verify(clienteJpaRepository, times(1)).save(any());
	}
}
