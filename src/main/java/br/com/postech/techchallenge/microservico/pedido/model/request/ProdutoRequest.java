package br.com.postech.techchallenge.microservico.pedido.model.request;

import java.math.BigDecimal;
import java.util.List;

public record ProdutoRequest(Long id, String nome, Integer categoria, BigDecimal valor, String descricao, List<ProdutoImagem> imagens) {

}
