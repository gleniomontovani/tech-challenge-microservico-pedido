package br.com.postech.techchallenge.microservico.pedido.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import br.com.postech.techchallenge.microservico.pedido.comum.util.CpfCnpjUtil;
import br.com.postech.techchallenge.microservico.pedido.configuration.ModelMapperConfiguration;
import br.com.postech.techchallenge.microservico.pedido.entity.Cliente;
import br.com.postech.techchallenge.microservico.pedido.exception.BusinessException;
import br.com.postech.techchallenge.microservico.pedido.model.request.ClienteRequest;
import br.com.postech.techchallenge.microservico.pedido.model.response.ClienteResponse;
import br.com.postech.techchallenge.microservico.pedido.repository.ClienteJpaRepository;
import br.com.postech.techchallenge.microservico.pedido.service.ClienteService;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteJpaRepository clienteJpaRepository;
    private static final ModelMapper MAPPER = ModelMapperConfiguration.getModelMapper();

    @Override
    public List<ClienteResponse> listarClientesAtivos() {
        List<Cliente> clientesAtivos = clienteJpaRepository.findByStatus(Boolean.TRUE);
        
        MAPPER.typeMap(Cliente.class, ClienteResponse.class)
		.addMappings(mapper -> {
			  mapper.map(src -> src.getId(),ClienteResponse::setNumero);
		});
        
        return MAPPER.map(clientesAtivos, new TypeToken<List<ClienteResponse>>() {}.getType());
    }

    @Override
    public ClienteResponse findById(Integer id) {
    	MAPPER.typeMap(Cliente.class, ClienteResponse.class)
		.addMappings(mapper -> {
			  mapper.map(src -> src.getId(),ClienteResponse::setNumero);
		});
    	
        Cliente cliente = clienteJpaRepository
        		.findByIdAndStatus(id, Boolean.TRUE)
        		.orElseThrow(() -> new BusinessException("Cliente não encontrado!"));        
          
        return MAPPER.map(cliente, ClienteResponse.class);
    }

    @Override
    public ClienteResponse save(ClienteRequest clienteRequest) {
        var cliente = MAPPER.map(clienteRequest, Cliente.class);
        
        if(Objects.nonNull(cliente.getCpf())) {
        	cliente.setCpf(CpfCnpjUtil.removeMaskCPFCNPJ(cliente.getCpf()));
        }
        cliente.setStatus(Boolean.TRUE);
        
        cliente = clienteJpaRepository.save(cliente);
        
        MAPPER.typeMap(Cliente.class, ClienteResponse.class)
		.addMappings(mapper -> {
			  mapper.map(src -> src.getId(),ClienteResponse::setNumero);
		});

        return MAPPER.map(cliente, ClienteResponse.class);
    }

    @Override
    public ClienteResponse atualizarCliente(Integer id, ClienteRequest clienteRequest) {
        Cliente clienteMappado = clienteJpaRepository.findById(id)
                .map(cliente -> {
                            cliente.setNome(clienteRequest.nome());
                            cliente.setEmail(clienteRequest.email());
                            cliente.setCpf(clienteRequest.cpf());
                            return clienteJpaRepository.save(cliente);
                        }
                ).orElseThrow(() -> new BusinessException("Cliente não encontrado!"));
        
        MAPPER.typeMap(Cliente.class, ClienteResponse.class)
		.addMappings(mapper -> {
			  mapper.map(src -> src.getId(),ClienteResponse::setNumero);
		});
        
        return MAPPER.map(clienteMappado, ClienteResponse.class);
    }

    @Override
    public ClienteResponse desativarCliente(Integer id) {
        Cliente cliente = clienteJpaRepository
        		.findById(id)
        		.orElseThrow(() -> new BusinessException("Cliente não encontrado!"));

        cliente.setStatus(Boolean.FALSE);
        cliente = clienteJpaRepository.save(cliente);

        return MAPPER.map(cliente, ClienteResponse.class);
    }
}
