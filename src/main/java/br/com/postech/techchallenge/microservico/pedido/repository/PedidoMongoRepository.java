package br.com.postech.techchallenge.microservico.pedido.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.postech.techchallenge.microservico.pedido.domain.PedidoDocumento;

@Repository
public interface PedidoMongoRepository extends MongoRepository<PedidoDocumento, Long>{

	@Query("{ 'numeroPedido' : ?0 }")
	Optional<PedidoDocumento> findByNumeroPedido(Long numeroPedido);
}
