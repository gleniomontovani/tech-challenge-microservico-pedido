package br.com.postech.techchallenge.microservico.pedido.service;

import java.util.List;

import br.com.postech.techchallenge.microservico.pedido.model.request.ClienteRequest;
import br.com.postech.techchallenge.microservico.pedido.model.response.ClienteResponse;

public interface ClienteService {

    List<ClienteResponse> listarClientesAtivos();

    ClienteResponse findById(Integer id);

    ClienteResponse save(ClienteRequest clienteRequest);

    ClienteResponse atualizarCliente(Integer id, ClienteRequest clienteRequest);

    ClienteResponse desativarCliente(Integer id);
}
