package br.com.postech.techchallenge.microservico.pedido.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.postech.techchallenge.microservico.pedido.entity.Cliente;
import br.com.postech.techchallenge.microservico.pedido.exception.PersistenceException;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteJpaRepository extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByStatus(Boolean c);

    Optional<Cliente> findByIdAndStatus(Integer id, Boolean status);

    Optional<Cliente> findById(Integer id);

    Optional<Cliente> findByCpfOrNomeOrEmail(String cpf, String nome, String email) throws PersistenceException;
}
