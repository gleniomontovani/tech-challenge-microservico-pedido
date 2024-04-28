package br.com.postech.techchallenge.microservico.pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import br.com.postech.techchallenge.microservico.pedido.comum.enums.CategoriaEnum;
import br.com.postech.techchallenge.microservico.pedido.comum.enums.StatusPedidoEnum;
import br.com.postech.techchallenge.microservico.pedido.comum.util.Constantes;
import br.com.postech.techchallenge.microservico.pedido.entity.Cliente;
import br.com.postech.techchallenge.microservico.pedido.entity.Pedido;
import br.com.postech.techchallenge.microservico.pedido.entity.Produto;
import br.com.postech.techchallenge.microservico.pedido.model.request.ClienteRequest;

public class ObjectCreatorHelper {
	
	public static Cliente obterCliente() {
		return Cliente.builder()
				.email(Constantes.CLIENTE_EMAIL)
				.nome(Constantes.CLIENTE_NAME)
				.senha(Constantes.SECRET_CLIENTE_ID)
				.status(Boolean.TRUE)
				.build();
	}
	
	public static Produto obterProduto() {
		return Produto.builder()
				.categoria(CategoriaEnum.BEBIDA)
				.descricao(Constantes.PRODUTO_DESCRICAO)
				.nome(Constantes.PRODUTO_NOME)
				.imagens(new ArrayList<>())
				.valor(BigDecimal.valueOf(100))
				.pedidos(new ArrayList<>())
				.build();
	}
	
	public static Pedido obterPedido() {
		var cliente = obterCliente();
		cliente.setId(1L);
		return Pedido.builder()
				.dataPedido(LocalDateTime.now())
				.cliente(cliente)
				.statusPedido(StatusPedidoEnum.RECEBIDO)
				.produtos(new ArrayList<>())
				.build();
	}
	
	public static ClienteRequest obterClienteRequest() {
		return new ClienteRequest(Constantes.CLIENTE_NAME, Constantes.CLIENTE_EMAIL, Constantes.CLIENTE_CPF_1, Constantes.SECRET_CLIENTE_ID);
	}
}
