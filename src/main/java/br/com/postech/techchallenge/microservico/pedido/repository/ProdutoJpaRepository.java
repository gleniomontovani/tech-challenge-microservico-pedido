package br.com.postech.techchallenge.microservico.pedido.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.postech.techchallenge.microservico.pedido.enums.CategoriaEnum;
import br.com.postech.techchallenge.microservico.pedido.model.Produto;

import java.util.List;

@Repository
public interface ProdutoJpaRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByCategoria(CategoriaEnum categoria);

}
