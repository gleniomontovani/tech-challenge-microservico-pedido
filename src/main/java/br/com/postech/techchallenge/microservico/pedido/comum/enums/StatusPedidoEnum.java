package br.com.postech.techchallenge.microservico.pedido.comum.enums;

public enum StatusPedidoEnum implements APIEnum{

	DESCONHECIDO(0, "Desconhecido"),
	RECEBIDO(1, "Recebido"),
	AGUARDANDO_PAGAMENTO(2, "Aguardando Pagamento"),
	PAGO(3, "Pago"),
	EM_PREPARACAO(4, "Em preparação"),
	PRONTO(5, "Pronto"),
	ENTREGUE(6, "Entregue");
	
	private Integer value;
	private String descricao;
	
	private StatusPedidoEnum(Integer value, String descricao) {
		this.value = value;
		this.descricao = descricao;
	}

	@Override
	public Integer getValue() {
		return value;
	}
	

	public String getDescricao() {
		return descricao;
	}

	public static StatusPedidoEnum get(Integer value) {
		for (StatusPedidoEnum status : StatusPedidoEnum.values()) {
			if(status.getValue() == value ) {
				return status;
			}
		}
		return StatusPedidoEnum.DESCONHECIDO;
	}
	
	public static StatusPedidoEnum getByDescricao(String descricao) {
		for (StatusPedidoEnum status : StatusPedidoEnum.values()) {
			if(status.getDescricao().equals(descricao)) {
				return status;
			}
		}
		return StatusPedidoEnum.DESCONHECIDO;
	}
}
