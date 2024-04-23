package br.com.postech.techchallenge.microservico.pedido.comum.converts;

import org.modelmapper.AbstractConverter;

import br.com.postech.techchallenge.microservico.pedido.comum.enums.CategoriaEnum;

public class InteiroParaCategoriaConverter extends AbstractConverter<Integer, CategoriaEnum> {

	@Override
	protected CategoriaEnum convert(Integer source) {
		return CategoriaEnum.get(source);
	}

}
