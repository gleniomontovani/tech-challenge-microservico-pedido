package br.com.postech.techchallenge.microservico.pedido.comum.converts;

import org.modelmapper.AbstractConverter;

import br.com.postech.techchallenge.microservico.pedido.entity.Cliente;
import br.com.postech.techchallenge.microservico.pedido.model.response.ClienteResponse;

public class ClienteParaClienteResponseConverter extends AbstractConverter<Cliente, ClienteResponse> {

	@Override
	protected ClienteResponse convert(Cliente cliente) {
		return ClienteResponse.builder()
				.numero(cliente.getId())
				.cpf(cliente.getCpf())
				.email(cliente.getEmail())
				.nome(cliente.getNome())
				.status(cliente.isStatus())
				.build();
	}

}
