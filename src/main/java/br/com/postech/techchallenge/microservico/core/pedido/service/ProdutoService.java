package br.com.postech.techchallenge.microservico.core.pedido.service;

import java.util.List;

import br.com.postech.techchallenge.microservico.core.pedido.model.request.ProdutoRequest;
import br.com.postech.techchallenge.microservico.core.pedido.model.response.ProdutoResponse;

public interface ProdutoService {

    List<ProdutoResponse> findByCategoria(Integer idCategoria);

    ProdutoResponse findById(Long id);

    ProdutoResponse save(ProdutoRequest produtoRequest);

    ProdutoResponse atualizar(Long id, ProdutoRequest produtoRequest);
    
    void deleteById(Long id);

}
