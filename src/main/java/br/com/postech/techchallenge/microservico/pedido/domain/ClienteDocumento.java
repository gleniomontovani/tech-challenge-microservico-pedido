package br.com.postech.techchallenge.microservico.pedido.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "cliente")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDocumento {

	private Long numero; 
	private String nome; 
	private String email; 
	private String cpf; 
	private Boolean status;
}
