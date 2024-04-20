package br.com.postech.techchallenge.microservico.core.pedido.model.request;


public record ClienteRequest(String nome, String email, String cpf, String senha) {

}
