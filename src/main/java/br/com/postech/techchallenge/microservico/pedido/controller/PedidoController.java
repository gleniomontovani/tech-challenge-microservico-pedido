package br.com.postech.techchallenge.microservico.pedido.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping("/v1/pedidos")
public class PedidoController {

//    @Autowired
//    private PedidoService pedidoService;

    @GetMapping(produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<Object>> listarTodosPedidosAtivos() throws Exception {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/{idPedido}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Object> buscarPedido(@PathVariable Integer idPedido) throws Exception {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/checkout", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Object> fazerCheckoutFake(@RequestBody Object pedidoDTO) throws Exception {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
