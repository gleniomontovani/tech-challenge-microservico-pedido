package br.com.postech.techchallenge.microservico.pedido.comum.converts;

import org.modelmapper.AbstractConverter;

import br.com.postech.techchallenge.microservico.pedido.comum.enums.StatusPagamentoEnum;

public class InteiroParaStatusPagamentoConverter extends AbstractConverter<Integer, StatusPagamentoEnum> {

	@Override
	protected StatusPagamentoEnum convert(Integer source) {
		return StatusPagamentoEnum.get(source);
	}

}
