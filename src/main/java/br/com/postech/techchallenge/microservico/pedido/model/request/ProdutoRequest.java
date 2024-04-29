package br.com.postech.techchallenge.microservico.pedido.model.request;

import java.math.BigDecimal;
import java.util.List;

import br.com.postech.techchallenge.microservico.pedido.model.ProdutoImagemModel;

public record ProdutoRequest(Long id, String nome, Integer categoria, BigDecimal valor, String descricao, List<ProdutoImagemModel> imagens) {

}
