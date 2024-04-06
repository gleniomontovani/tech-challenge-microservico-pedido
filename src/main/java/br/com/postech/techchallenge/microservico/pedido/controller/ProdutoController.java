package br.com.postech.techchallenge.microservico.pedido.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.postech.techchallenge.microservico.pedido.enums.CategoriaEnum;

import java.util.List;

@RestController
@RequestMapping("/v1/produtos")
@RequiredArgsConstructor
public class ProdutoController {

//    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid Object produtoDTO) {
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
//        this.produtoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Object> atualizar(@RequestBody @Valid Object produtoDTO) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Object>> listarProdutos(@RequestParam(required = false) CategoriaEnum categoria) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarProdutoPorId(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}