package br.com.postech.techchallenge.microservico.pedido.comum.converts;

import org.modelmapper.AbstractConverter;

import br.com.postech.techchallenge.microservico.pedido.comum.enums.CategoriaEnum;

public class CategoriaParaInteiroConverter extends AbstractConverter<CategoriaEnum, Integer> {

	@Override
	protected Integer convert(CategoriaEnum source) {
		return source == null ? null : source.getValue();
	}
}
