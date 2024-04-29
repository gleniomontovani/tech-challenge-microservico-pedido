package br.com.postech.techchallenge.microservico.pedido.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoImagemModel {

	@NotNull(message = "É obrigatório informar o caminho da imagem.")
	private String path;
}
