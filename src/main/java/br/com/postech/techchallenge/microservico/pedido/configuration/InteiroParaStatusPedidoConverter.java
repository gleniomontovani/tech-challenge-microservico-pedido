package br.com.postech.techchallenge.microservico.pedido.configuration;

import org.modelmapper.AbstractConverter;

import br.com.postech.techchallenge.microservico.pedido.enums.StatusPedidoEnum;

public class InteiroParaStatusPedidoConverter extends AbstractConverter<Integer, StatusPedidoEnum> {

	@Override
	protected StatusPedidoEnum convert(Integer source) {
		return StatusPedidoEnum.get(source);
	}

}
