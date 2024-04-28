package br.com.postech.techchallenge.microservico.pedido.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import br.com.postech.techchallenge.microservico.pedido.comum.enums.CategoriaEnum;
import br.com.postech.techchallenge.microservico.pedido.comum.util.Constantes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "produto")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false, length = 500)
    private String descricao;

    @Type(value = br.com.postech.techchallenge.microservico.pedido.comum.enums.AssociacaoType.class,
            parameters = {@Parameter(name = Constantes.ENUM_CLASS_NAME, value = "CategoriaEnum")})
    @Column(name = "categoria_id")
    private CategoriaEnum categoria;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProdutoImages> imagens;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PedidoProduto> pedidos;

}