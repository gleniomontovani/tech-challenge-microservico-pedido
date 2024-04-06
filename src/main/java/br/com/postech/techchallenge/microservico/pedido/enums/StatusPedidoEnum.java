package br.com.postech.techchallenge.microservico.pedido.enums;

public enum StatusPedidoEnum implements APIEnum{

	DESCONHECIDO(0, "Desconhecido"),
	RECEBIDO(1, "Recebido"),
	EM_PREPARACAO(2, "Em preparação"),
	PRONTO(3, "Pronto"),
	FINALIZADO(4, "Finalizado");
	
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
