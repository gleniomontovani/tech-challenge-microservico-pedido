package br.com.postech.techchallenge.microservico.pedido.model.request;

import java.math.BigDecimal;

public record PagamentoRequest(Long numeroPagamento, Long numeroPedido, Integer statusPagamento, BigDecimal valor) {

}
