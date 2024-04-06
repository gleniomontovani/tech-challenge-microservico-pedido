package br.com.postech.techchallenge.microservico.pedido.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping("/v1/clientes")
public class ClienteController {

    @GetMapping(produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<Object>> listarClientes() {
//        List<ClienteDTO> clientesAtivos = clienteService.listarClientesAtivos();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "{idCliente}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Object> buscarCliente(@PathVariable("idCliente") Integer idCliente) throws Exception {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Object> salvarCliente(@RequestBody @Valid Object clienteDTO) throws Exception {
//        clienteDTO.setStatus(Boolean.TRUE);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Object> atualizarCliente(@PathVariable Integer id, @RequestBody Object clienteDTO) {
//        ClienteDTO updatedClienteDTO = clienteService.atualizarCliente(id, clienteDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/desativar/{id}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Object> desativarCliente(@PathVariable Integer id) {
//        ClienteDTO clienteDTO = clienteService.desativarCliente(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}