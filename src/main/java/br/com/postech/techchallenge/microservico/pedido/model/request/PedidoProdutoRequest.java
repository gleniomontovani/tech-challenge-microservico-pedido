package br.com.postech.techchallenge.microservico.pedido.model.request;

public record PedidoProdutoRequest(ProdutoRequest produto, Integer quantidade) {

}
