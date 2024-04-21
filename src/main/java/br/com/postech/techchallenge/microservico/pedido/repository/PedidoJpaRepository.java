package br.com.postech.techchallenge.microservico.pedido.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.postech.techchallenge.microservico.pedido.comum.enums.StatusPedidoEnum;
import br.com.postech.techchallenge.microservico.pedido.entity.Pedido;
import br.com.postech.techchallenge.microservico.pedido.exception.PersistenceException;

@Repository
public interface PedidoJpaRepository extends JpaRepository<Pedido, Integer> {

	List<Pedido> findByStatusPedidoNotIn(List<StatusPedidoEnum> statusPedidos) throws PersistenceException;
}
