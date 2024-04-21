package br.com.postech.techchallenge.microservico.pedido.comum.converts;

import org.modelmapper.AbstractConverter;

import br.com.postech.techchallenge.microservico.pedido.comum.enums.StatusPedidoEnum;

public class StatusPedidoParaInteiroConverter extends AbstractConverter<StatusPedidoEnum, Integer> {

	@Override
	protected Integer convert(StatusPedidoEnum source) {
		return source == null ? null : source.getValue();
	}

}
