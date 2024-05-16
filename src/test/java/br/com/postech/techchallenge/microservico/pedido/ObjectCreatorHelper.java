package br.com.postech.techchallenge.microservico.pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import br.com.postech.techchallenge.microservico.pedido.comum.enums.CategoriaEnum;
import br.com.postech.techchallenge.microservico.pedido.comum.enums.StatusPedidoEnum;
import br.com.postech.techchallenge.microservico.pedido.comum.util.Constantes;
import br.com.postech.techchallenge.microservico.pedido.entity.Cliente;
import br.com.postech.techchallenge.microservico.pedido.entity.Pedido;
import br.com.postech.techchallenge.microservico.pedido.entity.Produto;
import br.com.postech.techchallenge.microservico.pedido.model.request.ClienteRequest;
import br.com.postech.techchallenge.microservico.pedido.model.request.PedidoProdutoRequest;
import br.com.postech.techchallenge.microservico.pedido.model.request.PedidoRequest;
import br.com.postech.techchallenge.microservico.pedido.model.request.ProdutoRequest;
import br.com.postech.techchallenge.microservico.pedido.model.response.ClienteResponse;
import br.com.postech.techchallenge.microservico.pedido.model.response.PagamentoResponse;
import br.com.postech.techchallenge.microservico.pedido.model.response.PedidoResponse;
import br.com.postech.techchallenge.microservico.pedido.model.response.ProdutoResponse;

public class ObjectCreatorHelper {
	
	public static Cliente obterCliente() {
		return Cliente.builder()
				.email(Constantes.CLIENTE_EMAIL)
				.nome(Constantes.CLIENTE_NAME)
				.senha(Constantes.SECRET_CLIENTE_ID)
				.status(Boolean.TRUE)
				.build();
	}
	
	public static ClienteRequest obterClienteRequest() {
		return new ClienteRequest(Constantes.CLIENTE_NAME, Constantes.CLIENTE_EMAIL, Constantes.CLIENTE_CPF_1, Constantes.SECRET_CLIENTE_ID);
	}
	
	public static ClienteResponse obterClienteResponse() {
		return ClienteResponse.builder()
				.email(Constantes.CLIENTE_EMAIL)
				.nome(Constantes.CLIENTE_NAME)
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
	
	public static ProdutoRequest obterProdutoRequest() {
		return new ProdutoRequest(1L, Constantes.PRODUTO_NOME, CategoriaEnum.BEBIDA.getValue(), BigDecimal.valueOf(100), Constantes.PRODUTO_DESCRICAO, new ArrayList<>());
	}
	
	public static ProdutoResponse obterProdutoResponse() {
		return ProdutoResponse.builder()
				.nome(Constantes.PRODUTO_NOME)
				.categoria(CategoriaEnum.BEBIDA.getValue())
				.descricao(Constantes.PRODUTO_DESCRICAO)
				.valor(BigDecimal.valueOf(100))
				.imagens(new ArrayList<>())
				.build();
	}
	
	public static ProdutoRequest obterProdutoRequestSemImagens() {
		return new ProdutoRequest(1L, Constantes.PRODUTO_NOME, CategoriaEnum.BEBIDA.getValue(), BigDecimal.valueOf(100), Constantes.PRODUTO_DESCRICAO, null);
	}	
	
	public static Pedido obterPedido() {
		var cliente = obterCliente();
		cliente.setId(1L);
		cliente.setCpf(Constantes.CLIENTE_CPF_1);
		return Pedido.builder()
				.dataPedido(LocalDateTime.now())
				.cliente(cliente)
				.statusPedido(StatusPedidoEnum.RECEBIDO)
				.produtos(new ArrayList<>())
				.build();
	}
	
	public static PedidoRequest obterPedidoRequest() {		
		return new PedidoRequest(1L, obterClienteRequest(), LocalDateTime.now().toString(), StatusPedidoEnum.RECEBIDO.getValue(), Arrays.asList(obterPedidoProdutoRequest()));
	}
	
	public static PedidoRequest obterPedidoRequestSemProduto() {		
		return new PedidoRequest(1L, obterClienteRequest(), LocalDateTime.now().toString(), StatusPedidoEnum.RECEBIDO.getValue(), new ArrayList<>());
	}
	
	public static PedidoRequest obterPedidoRequestSemID() {		
		return new PedidoRequest(null, null, LocalDateTime.now().toString(), StatusPedidoEnum.RECEBIDO.getValue(), Arrays.asList(obterPedidoProdutoRequest()));
	}
	
	public static PedidoRequest obterPedidoRequestProdutoNulo() {		
		return new PedidoRequest(1L, obterClienteRequest(), LocalDateTime.now().toString(), StatusPedidoEnum.RECEBIDO.getValue(), null);
	}
	
	public static PedidoResponse obterPedidoResponse() {
		return PedidoResponse.builder()
				.cliente(obterClienteResponse())
				.dataPedido(LocalDateTime.now().toString())
				.statusPedido(StatusPedidoEnum.RECEBIDO.getValue())
				.statusPagamento(1)
				.build();
	}
	
	public static PagamentoResponse obterPagamentoResponse() {
		return new PagamentoResponse(1L, 1L, LocalDateTime.now().toString(), 1, BigDecimal.valueOf(100), null);
	}
			
	public static PedidoProdutoRequest obterPedidoProdutoRequest() {
		return new PedidoProdutoRequest(obterProdutoRequest(), 10);
	}
}
