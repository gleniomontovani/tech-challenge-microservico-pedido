package br.com.postech.techchallenge.microservico.pedido.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponse {

	private Long numeroPedido;
    private ClienteResponse cliente;
    private String dataPedido;
    private Integer statusPedido;
    private String statusPagamento;
}
