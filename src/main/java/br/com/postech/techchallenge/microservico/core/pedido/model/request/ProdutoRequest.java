package br.com.postech.techchallenge.microservico.core.pedido.model.request;

import java.math.BigDecimal;
import java.util.List;

public record ProdutoRequest(String nome, Integer categoria, BigDecimal valor, String descricao, List<ProdutoImagem> imagens) {

}
