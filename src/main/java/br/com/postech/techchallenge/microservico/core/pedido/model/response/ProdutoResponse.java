package br.com.postech.techchallenge.microservico.core.pedido.model.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoResponse {

	private Long id;
    private String nome;
    private String categoria;
    private BigDecimal valor;
    private String descricao;
    private List<String> imagens;
}
