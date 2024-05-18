package br.com.postech.techchallenge.microservico.pedido.unit.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
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
import br.com.postech.techchallenge.microservico.pedido.entity.Cliente;
import br.com.postech.techchallenge.microservico.pedido.repository.ClienteJpaRepository;

class ClienteJpaRepositoryTest {
	
	@Mock
	private ClienteJpaRepository clienteJpaRepository;

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
	void devePermitirBuscarClientePorStatus() {
		var cliente1 = ObjectCreatorHelper.obterCliente();
		cliente1.setId(1L);
		cliente1.setCpf(Constantes.CLIENTE_CPF_1);
		
		var cliente2 = ObjectCreatorHelper.obterCliente();
		cliente2.setId(2L);
		cliente2.setCpf(Constantes.CLIENTE_CPF_2);
		
		var clientes = Arrays.asList(cliente1, cliente2);
		
		when(clienteJpaRepository.findByStatus(anyBoolean())).thenReturn(clientes);
		
		var clientesAtivos = clienteJpaRepository.findByStatus(Boolean.TRUE);
		
		verify(clienteJpaRepository, times(1)).findByStatus(anyBoolean());
		
		assertThat(clientesAtivos).hasSize(2).containsExactlyInAnyOrder(cliente1, cliente2);		
	}

	@Test
	void devePermitirBuscarClientePor_Id_E_Por_Status() {
		var cliente = ObjectCreatorHelper.obterCliente();
		cliente.setId(1L);
		cliente.setCpf(Constantes.CLIENTE_CPF_1);
		
		when(clienteJpaRepository.findByIdAndStatus(anyInt(), anyBoolean())).thenReturn(Optional.of(cliente));
		
		var clienteOptional = clienteJpaRepository.findByIdAndStatus(1, Boolean.TRUE);
		
		assertThat(clienteOptional).isPresent().containsSame(cliente);
		
		clienteOptional.ifPresent(clienteEncontrado -> {
			assertThat(clienteEncontrado).isNotNull().isInstanceOf(Cliente.class);
			assertThat(clienteEncontrado.getId()).isEqualTo(cliente.getId());
			assertThat(clienteEncontrado.getCpf()).isEqualTo(cliente.getCpf());
			assertThat(clienteEncontrado.getEmail()).isEqualTo(cliente.getEmail());
			assertThat(clienteEncontrado.getNome()).isEqualTo(cliente.getNome());
			assertThat(clienteEncontrado.getSenha()).isEqualTo(cliente.getSenha());
		});
				
		verify(clienteJpaRepository, times(1)).findByIdAndStatus(anyInt(), anyBoolean());
	}

	@Test
	void devePermitirBuscarClientePorId() {
		var cliente = ObjectCreatorHelper.obterCliente();
		cliente.setId(1L);
		cliente.setCpf(Constantes.CLIENTE_CPF_1);
		
		when(clienteJpaRepository.findById(anyInt())).thenReturn(Optional.of(cliente));
		
		var clienteOptional = clienteJpaRepository.findById(1);
		
		assertThat(clienteOptional).isPresent().containsSame(cliente);
		
		clienteOptional.ifPresent(clienteEncontrado -> {
			assertThat(clienteEncontrado).isNotNull().isInstanceOf(Cliente.class);
			assertThat(clienteEncontrado.getId()).isEqualTo(cliente.getId());
			assertThat(clienteEncontrado.getCpf()).isEqualTo(cliente.getCpf());
			assertThat(clienteEncontrado.getEmail()).isEqualTo(cliente.getEmail());
			assertThat(clienteEncontrado.getNome()).isEqualTo(cliente.getNome());
			assertThat(clienteEncontrado.getSenha()).isEqualTo(cliente.getSenha());
		});
				
		verify(clienteJpaRepository, times(1)).findById(anyInt());
	}

	@Test
	void devePermitirBuscarClientePor_CPF_OU_Nome_OU_Email() {
		var cliente = ObjectCreatorHelper.obterCliente();
		cliente.setId(1L);
		cliente.setCpf(Constantes.CLIENTE_CPF_1);
		
		when(clienteJpaRepository.findByCpfOrNomeOrEmail(anyString(), anyString(), anyString())).thenReturn(Optional.of(cliente));
		
		var clienteOptional = clienteJpaRepository.findByCpfOrNomeOrEmail(Constantes.CLIENTE_CPF_1,
				Constantes.CLIENTE_NAME, Constantes.CLIENTE_EMAIL);
		
		assertThat(clienteOptional).isPresent().contains(cliente);
		
		clienteOptional.ifPresent(clienteEncontrado -> {
			assertThat(clienteEncontrado.getId()).isEqualTo(cliente.getId());
			assertThat(clienteEncontrado.getCpf()).isEqualTo(cliente.getCpf());
			assertThat(clienteEncontrado.getEmail()).isEqualTo(cliente.getEmail());
			assertThat(clienteEncontrado.getNome()).isEqualTo(cliente.getNome());
			assertThat(clienteEncontrado.getSenha()).isEqualTo(cliente.getSenha());
		});		
				
		verify(clienteJpaRepository, times(1)).findByCpfOrNomeOrEmail(anyString(), anyString(), anyString());
	}

	@Test
	void devePermitirSalvaCliente() {
		var cliente = ObjectCreatorHelper.obterCliente();
		cliente.setId(1L);
		cliente.setCpf(Constantes.CLIENTE_CPF_1);
		
		when(clienteJpaRepository.save(any())).thenReturn(cliente);
		
		var clienteSalvo = clienteJpaRepository.save(cliente);
		
		assertThat(clienteSalvo).isInstanceOf(Cliente.class).isNotNull().isEqualTo(cliente);
		assertThat(clienteSalvo).extracting(Cliente::getId).isEqualTo(cliente.getId());
		assertThat(clienteSalvo).extracting(Cliente::getNome).isEqualTo(cliente.getNome());
		assertThat(clienteSalvo).extracting(Cliente::getCpf).isEqualTo(cliente.getCpf());
		assertThat(clienteSalvo).extracting(Cliente::getEmail).isEqualTo(cliente.getEmail());
		assertThat(clienteSalvo).extracting(Cliente::getSenha).isEqualTo(cliente.getSenha());
		
		verify(clienteJpaRepository, times(1)).save(any());
	}
}