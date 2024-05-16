package br.com.postech.techchallenge.microservico.pedido.bdd;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import br.com.postech.techchallenge.microservico.pedido.ObjectCreatorHelper;
import br.com.postech.techchallenge.microservico.pedido.comum.enums.StatusPedidoEnum;
import br.com.postech.techchallenge.microservico.pedido.configuration.ControllerMappingConfig;
import br.com.postech.techchallenge.microservico.pedido.model.request.PedidoRequest;
import br.com.postech.techchallenge.microservico.pedido.model.response.PedidoResponse;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;

public class StepDefinitionPedido {
	
	private Response response;
	private PedidoResponse pedidoResponse;
	
	// - ###################################################
	// - Salvar um novo pedido
	// - ###################################################
	@Quando("submeter uma nova requisição de criação de pedido")
	public PedidoResponse submeter_uma_nova_requisição_de_criação_de_pedido() {
	    var pedidoRequest = ObjectCreatorHelper.obterPedidoRequestSemID();
	    
	    response = given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(pedidoRequest)
				.when()
				.post(ControllerMappingConfig.ENDPOINT_MICRO_SERVICE_PEDIDO_LOCAL+"/checkout");	
	    
	    return response.then().extract().as(PedidoResponse.class);
	}
	
	@Então("o pedido é registrado com sucesso")
	public void o_pedido_é_registrado_com_sucesso() {
		response.then()
	        .statusCode(HttpStatus.CREATED.value())
	        .body(matchesJsonSchemaInClasspath("./schemas/PedidoResponseSchema.json"));
	}
	
	// - ###################################################	
	// - Buscar pedido pelo número
	// - ###################################################	
	@Dado("que o pedido já foi cadastrado")
	public void que_o_pedido_já_foi_cadastrado() {
		pedidoResponse = submeter_uma_nova_requisição_de_criação_de_pedido();
	}
	
	@Quando("requisitar a busca de um pedido pelo número")
	public void requisitar_a_busca_de_um_pedido_pelo_número() {
		response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(ControllerMappingConfig.ENDPOINT_MICRO_SERVICE_PEDIDO_LOCAL+"/{idPedido}", 
                	 pedidoResponse.getNumeroPedido());
	}
	
	@Então("o pedido é exibido com sucesso")
	public void o_pedido_é_exibido_com_sucesso() {
		response.then()
        	.statusCode(HttpStatus.OK.value())
        	.body(matchesJsonSchemaInClasspath("./schemas/PedidoResponseSchema.json"));
	}

	// - ###################################################	
	// - Listar produtos de um pedido
	// - ###################################################
	@Quando("requisitar a lista dos produtos de um pedido")
	public void requisitar_a_lista_dos_produtos_de_um_pedido() {
		response = given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.when()
					.get(ControllerMappingConfig.ENDPOINT_MICRO_SERVICE_PEDIDO_LOCAL + "/produtos/{numeroPedido}",
						 pedidoResponse.getNumeroPedido()
						);
	}
	
	@Então("os produtos desse pedido é exibido com sucesso")
	public void os_produtos_desse_pedido_é_exibido_com_sucesso() {
		response.then()
	        .statusCode(HttpStatus.OK.value())
	        .body(matchesJsonSchemaInClasspath("./schemas/PedidoProdutoResponseSchema.json"));
	}
	
	// - ###################################################
	// - Lista Pedidos Ativos
	// - ###################################################
	@Quando("requisitar a lista de pedidos ativos")
	public void requisitar_a_lista_de_pedidos_ativos() {
		response = given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.when()
					.get(ControllerMappingConfig.ENDPOINT_MICRO_SERVICE_PEDIDO_LOCAL);
	}
	
	@Então("os pedidos ativos serão exibidos com sucesso")
	public void os_pedidos_ativos_serão_exibidos_com_sucesso() {
		response.then()
	        .statusCode(HttpStatus.OK.value())
	        .body(matchesJsonSchemaInClasspath("./schemas/ListaPedidoResponseSchema.json"));
	}
	
	// - ###################################################
	// - Alteração de Pedido
	// - ###################################################
	@Quando("requisitar um alteração do pedido")
	public void requisitar_um_alteração_do_pedido() {
	    var pedidoRequest = new PedidoRequest(pedidoResponse.getNumeroPedido(), 
	    									  null, 
	    									  LocalDateTime.now().toString(), 
	    									  StatusPedidoEnum.PAGO.getValue(), 
	    									  Arrays.asList(ObjectCreatorHelper.obterPedidoProdutoRequest()));
	    
	    response = given()
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.body(pedidoRequest)
				.when()
				.put(ControllerMappingConfig.ENDPOINT_MICRO_SERVICE_PEDIDO_LOCAL);
	}
	
	@Então("o pedido é atualizado com sucesso")
	public void o_pedido_é_atualizado_com_sucesso() {
		response.then()
			.statusCode(HttpStatus.OK.value())
			.body(matchesJsonSchemaInClasspath("./schemas/PedidoResponseSchema.json"));
	}
}
