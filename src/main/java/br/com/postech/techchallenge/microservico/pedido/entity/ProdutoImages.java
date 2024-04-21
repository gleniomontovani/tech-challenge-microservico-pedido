package br.com.postech.techchallenge.microservico.pedido.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "produto_images")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoImages implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Caminho da imagem é obrigatório.")
    @Column(nullable = false, length = 500)
    private String path;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "produto_id")
    private Produto produto;

}
