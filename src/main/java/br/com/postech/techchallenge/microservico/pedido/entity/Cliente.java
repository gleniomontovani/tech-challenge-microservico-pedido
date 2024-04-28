package br.com.postech.techchallenge.microservico.pedido.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import org.hibernate.type.TrueFalseConverter;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Cliente implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = true, length = 255)
	private String nome;

	@Column(nullable = true, unique = true, length = 14)
	private String cpf;

	@Column(nullable = true, length = 255)
	private String email;

	@Column(nullable = true,length = 255)
	private String senha;

	@Convert(converter = TrueFalseConverter.class)
	@Column(nullable = false)
	private boolean status;

}
