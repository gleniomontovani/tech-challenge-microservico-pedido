package br.com.postech.techchallenge.microservico.core.comum.converts;

import org.modelmapper.AbstractConverter;

import br.com.postech.techchallenge.microservico.core.comum.enums.StatusPagamentoEnum;

public class StatusPagamentoParaInteiroConverter extends AbstractConverter<StatusPagamentoEnum, Integer>{

	@Override
	protected Integer convert(StatusPagamentoEnum source) {
		return source == null ? null : source.getValue();
	}

}
