package br.com.postech.techchallenge.microservico.pedido.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.postech.techchallenge.microservico.pedido.enums.StatusPedidoEnum;
import br.com.postech.techchallenge.microservico.pedido.exception.PersistenceException;
import br.com.postech.techchallenge.microservico.pedido.model.Pedido;

@Repository
public interface PedidoJpaRepository extends JpaRepository<Pedido, Integer> {

	List<Pedido> findByStatusPedidoNotIn(List<StatusPedidoEnum> statusPedidos) throws PersistenceException;
}
