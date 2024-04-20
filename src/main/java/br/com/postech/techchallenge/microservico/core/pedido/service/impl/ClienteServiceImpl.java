package br.com.postech.techchallenge.microservico.core.pedido.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.postech.techchallenge.microservico.core.comum.util.CpfCnpjUtil;
import br.com.postech.techchallenge.microservico.core.pedido.configuration.ModelMapperConfiguration;
import br.com.postech.techchallenge.microservico.core.pedido.entity.Cliente;
import br.com.postech.techchallenge.microservico.core.pedido.model.request.ClienteRequest;
import br.com.postech.techchallenge.microservico.core.pedido.model.response.ClienteResponse;
import br.com.postech.techchallenge.microservico.core.pedido.repository.ClienteJpaRepository;
import br.com.postech.techchallenge.microservico.core.pedido.service.ClienteService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteJpaRepository clienteJpaRepository;
    private static final ModelMapper MAPPER = ModelMapperConfiguration.getModelMapper();

    @Override
    public List<ClienteResponse> listarClientesAtivos() {
        List<Cliente> clientesAtivos = clienteJpaRepository.findByStatus(Boolean.TRUE);
        return clientesAtivos.stream()
                .map(cliente -> MAPPER.map(cliente, ClienteResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public ClienteResponse findById(Integer id) {
        Optional<Cliente> cliente = clienteJpaRepository.findByIdAndStatus(id, Boolean.TRUE);
        if (cliente.isPresent()) {
            return MAPPER.map(cliente.get(), ClienteResponse.class);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
    }

    @Override
    public ClienteResponse save(ClienteRequest clienteRequest) {
        var cliente = MAPPER.map(clienteRequest, Cliente.class);
        
        if(Objects.nonNull(cliente.getCpf())) {
        	cliente.setCpf(CpfCnpjUtil.removeMaskCPFCNPJ(cliente.getCpf()));
        }
        cliente.setStatus(Boolean.TRUE);
        
        cliente = clienteJpaRepository.save(cliente);

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
                ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

        return MAPPER.map(clienteMappado, ClienteResponse.class);
    }

    @Override
    public ClienteResponse desativarCliente(Integer id) {
        Optional<Cliente> clienteOptional = clienteJpaRepository.findById(id);

        if (!clienteOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }

        Cliente cliente = clienteOptional.get();
        cliente.setStatus(Boolean.FALSE);
        cliente = clienteJpaRepository.save(cliente);

        return MAPPER.map(cliente, ClienteResponse.class);
    }
}
