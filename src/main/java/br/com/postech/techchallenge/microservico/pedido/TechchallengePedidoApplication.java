package br.com.postech.techchallenge.microservico.pedido;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TechchallengePedidoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechchallengePedidoApplication.class, args);
	}
}