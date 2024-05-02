package br.com.postech.techchallenge.microservico.pedido.comum.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utilitario {

	public static String asJsonString(final Object obj) throws Exception {
		return new ObjectMapper().writeValueAsString(obj);
	}
}
