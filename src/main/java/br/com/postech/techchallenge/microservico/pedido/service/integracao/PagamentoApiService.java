package br.com.postech.techchallenge.microservico.pedido.service.integracao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.postech.techchallenge.microservico.pedido.configuration.ControllerMappingConfig;
import feign.Headers;

@FeignClient(url = "${api.client.pagamento.uri}", path = ControllerMappingConfig.PATH_API_PAGAMENTO, name = "pagamentos")
public interface PagamentoApiService {

	@RequestMapping(method = RequestMethod.GET, value = "/{numeroPedido}")
	@Headers("Accept: application/json")
	String consultarPagamentoPorPedido(@PathVariable("numeroPedido") Long numeroPedido) throws Exception;
}
