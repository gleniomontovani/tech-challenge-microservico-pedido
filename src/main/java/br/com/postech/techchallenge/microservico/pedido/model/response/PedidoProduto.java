package br.com.postech.techchallenge.microservico.pedido.model.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoProduto {

	private ProdutoResponse produto;
    @NotNull(message = "A quantidade é obrigatório.")
    private Integer quantidade;
}
