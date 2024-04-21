package br.com.postech.techchallenge.microservico.pedido.comum.converts;

import org.modelmapper.AbstractConverter;

import br.com.postech.techchallenge.microservico.pedido.comum.enums.StatusPedidoEnum;

public class InteiroParaStatusPedidoConverter extends AbstractConverter<Integer, StatusPedidoEnum> {

	@Override
	protected StatusPedidoEnum convert(Integer source) {
		return StatusPedidoEnum.get(source);
	}

}
