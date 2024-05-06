package br.com.postech.techchallenge.microservico.pedido.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.postech.techchallenge.microservico.pedido.model.request.PedidoRequest;
import br.com.postech.techchallenge.microservico.pedido.model.response.PedidoProdutoResponse;
import br.com.postech.techchallenge.microservico.pedido.model.response.PedidoResponse;
import br.com.postech.techchallenge.microservico.pedido.service.PedidoService;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping("/v1/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    private final PedidoService pedidoService;

    @GetMapping(produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<PedidoResponse>> listarTodosPedidosAtivos() throws Exception {
        return new ResponseEntity<>(pedidoService.findTodosPedidosAtivos(), HttpStatus.OK);
    }

    @GetMapping(path = "/{idPedido}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<PedidoResponse> buscarPedido(@PathVariable Integer idPedido) throws Exception {
        return new ResponseEntity<>(pedidoService.findById(idPedido), HttpStatus.OK);
    }

    @GetMapping(path = "/produtos/{numeroPedido}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<PedidoProdutoResponse>> buscarProdutosPorPedido(@PathVariable Integer numeroPedido) throws Exception {
    	return new ResponseEntity<>(pedidoService.findProdutosByPedido(numeroPedido), HttpStatus.OK);
    }
    
    @PostMapping(path = "/checkout", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<PedidoResponse> fazerCheckoutFake(@RequestBody PedidoRequest pedidoRequest) throws Exception {
        return new ResponseEntity<>(pedidoService.fazerPedidoFake(pedidoRequest), HttpStatus.CREATED);
    }
    
    @PutMapping(produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<PedidoResponse> atualizarPedido(@RequestBody PedidoRequest pedidoRequest) throws Exception {
        return new ResponseEntity<>(pedidoService.atualizarPedido(pedidoRequest), HttpStatus.OK);
    }   
}
