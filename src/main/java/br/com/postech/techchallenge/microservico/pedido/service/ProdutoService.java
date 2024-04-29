package br.com.postech.techchallenge.microservico.pedido.service;

import java.util.List;

import br.com.postech.techchallenge.microservico.pedido.exception.BusinessException;
import br.com.postech.techchallenge.microservico.pedido.model.request.ProdutoRequest;
import br.com.postech.techchallenge.microservico.pedido.model.response.ProdutoResponse;

public interface ProdutoService {

    List<ProdutoResponse> findByCategoria(Integer idCategoria);

    ProdutoResponse findById(Long id);

    ProdutoResponse save(ProdutoRequest produtoRequest)throws BusinessException;

    ProdutoResponse atualizar(Long id, ProdutoRequest produtoRequest);
    
    void deleteById(Long id);

}
