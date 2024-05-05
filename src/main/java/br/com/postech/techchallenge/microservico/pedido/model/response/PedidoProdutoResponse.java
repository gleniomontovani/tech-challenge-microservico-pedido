package br.com.postech.techchallenge.microservico.pedido.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoProdutoResponse {

	private ProdutoResponse produto;
    private Integer quantidade;
}
