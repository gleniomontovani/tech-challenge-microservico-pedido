package br.com.postech.techchallenge.microservico.pedido.model.request;

import java.util.List;

public record PedidoRequest(Long numeroPedido, ClienteRequest cliente, String dataPedido, Integer statusPedido,
		List<PedidoProdutoRequest> produtos) {

}
