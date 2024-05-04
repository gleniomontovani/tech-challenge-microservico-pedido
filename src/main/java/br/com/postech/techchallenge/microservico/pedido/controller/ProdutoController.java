package br.com.postech.techchallenge.microservico.pedido.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.postech.techchallenge.microservico.pedido.model.request.ProdutoRequest;
import br.com.postech.techchallenge.microservico.pedido.model.response.ProdutoResponse;
import br.com.postech.techchallenge.microservico.pedido.service.ProdutoService;

import java.util.List;

import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping("/v1/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoResponse> salvar(@RequestBody @Valid ProdutoRequest produtoRequest) {    	
        return new ResponseEntity<>(produtoService.save(produtoRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        produtoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ProdutoResponse> atualizar(@PathVariable Long id, @RequestBody @Valid ProdutoRequest produtoRequest) {
        return new ResponseEntity<>(produtoService.atualizar(id, produtoRequest), HttpStatus.OK);
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<ProdutoResponse>> listarProdutos(@RequestParam(required = false) Integer categoria) {
        return new ResponseEntity<>(produtoService.findByCategoria(categoria), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ProdutoResponse> buscarProdutoPorId(@PathVariable Long id) {
        return new ResponseEntity<>(produtoService.findById(id), HttpStatus.OK);
    }
}