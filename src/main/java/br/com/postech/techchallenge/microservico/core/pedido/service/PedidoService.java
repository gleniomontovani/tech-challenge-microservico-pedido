package br.com.postech.techchallenge.microservico.core.pedido.service;

import java.util.List;

import br.com.postech.techchallenge.microservico.core.pedido.exception.BusinessException;
import br.com.postech.techchallenge.microservico.core.pedido.model.request.PedidoRequest;
import br.com.postech.techchallenge.microservico.core.pedido.model.response.PedidoResponse;

public interface PedidoService {

	List<PedidoResponse> findTodosPedidosAtivos() throws BusinessException;
	
	PedidoResponse findById(Integer id) throws BusinessException;
	
	PedidoResponse fazerPedidoFake(PedidoRequest pedidoRequest) throws BusinessException;
}
