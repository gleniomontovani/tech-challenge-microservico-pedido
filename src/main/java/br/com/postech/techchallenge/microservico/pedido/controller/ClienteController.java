package br.com.postech.techchallenge.microservico.pedido.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.postech.techchallenge.microservico.pedido.model.request.ClienteRequest;
import br.com.postech.techchallenge.microservico.pedido.model.response.ClienteResponse;
import br.com.postech.techchallenge.microservico.pedido.service.ClienteService;

import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping("/v1/clientes")
@RequiredArgsConstructor
public class ClienteController {

	private final ClienteService clienteService;
	
    @GetMapping(produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<ClienteResponse>> listarClientes() {
        List<ClienteResponse> clientesAtivos = clienteService.listarClientesAtivos();
        return new ResponseEntity<>(clientesAtivos, HttpStatus.OK);
    }

    @GetMapping(path = "{idCliente}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ClienteResponse> buscarCliente(@PathVariable("idCliente") Integer idCliente) throws Exception {
        return new ResponseEntity<>(clienteService.findById(idCliente), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ClienteResponse> salvarCliente(@RequestBody @Valid ClienteRequest clienteRequest) throws Exception {       
        return new ResponseEntity<>(clienteService.save(clienteRequest), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ClienteResponse> atualizarCliente(@PathVariable Integer id, @RequestBody ClienteRequest clienteRequest) {
    	ClienteResponse updatedCliente = clienteService.atualizarCliente(id, clienteRequest);

        return new ResponseEntity<>(updatedCliente, HttpStatus.OK);
    }

    @PutMapping(value = "/desativar/{id}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ClienteResponse> desativarCliente(@PathVariable Integer id) {
        ClienteResponse cliente = clienteService.desativarCliente(id);

        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }
}