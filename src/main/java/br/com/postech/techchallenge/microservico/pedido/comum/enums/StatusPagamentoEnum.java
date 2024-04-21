package br.com.postech.techchallenge.microservico.pedido.comum.enums;

public enum StatusPagamentoEnum  implements APIEnum {

	DESCONHECIDO(0, "Desconhecido"),
	PENDENTE(1, "Pendente"),
	APROVADO(2, "Aprovado"),
	RECUSADO(3, "Recusado");
	
	private Integer value;
	private String descricao;
	
	private StatusPagamentoEnum(Integer value, String descricao) {
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
	
	public static StatusPagamentoEnum get(Integer value) {
		
		for (StatusPagamentoEnum status : StatusPagamentoEnum.values()) {
			if(status.getValue() == value) {
				return status;
			}
		}
		
		return StatusPagamentoEnum.DESCONHECIDO;
	}
	
	public static StatusPagamentoEnum getByDescricao(String descricao) {
		
		for (StatusPagamentoEnum status : StatusPagamentoEnum.values()) {
			if(status.getDescricao().equals(descricao)) {
				return status;
			}
		}
		
		return StatusPagamentoEnum.DESCONHECIDO;
	}
}
