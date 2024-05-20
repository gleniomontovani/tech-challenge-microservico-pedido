package br.com.postech.techchallenge.microservico.pedido.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "pedido")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDocumento {

	@Id
	private Long numeroPedido;
	private Long numeroPagamento;
    private ClienteDocumento cliente;
    private String dataPedido;
    private Integer statusPedido;
    private Integer statusPagamento;
    private String qrCodePix;
}
