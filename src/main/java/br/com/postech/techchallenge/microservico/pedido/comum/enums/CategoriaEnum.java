package br.com.postech.techchallenge.microservico.pedido.comum.enums;

public enum CategoriaEnum implements APIEnum{
	
	DESCONHECIDO(0, "Desconhecida"),
	LANCHE(1, "Lanche"),
	ACOMPANHAMENTO(2, "Acompanhamento"),
	BEBIDA(3, "Bebida"),
	SOBREMESA(4, "Sobremesa");
	
	private Integer value;
	private String descricao;
	
	private CategoriaEnum(Integer value, String descricao) {
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

	public static CategoriaEnum get(Integer value) {
		for (CategoriaEnum categoria : CategoriaEnum.values()) {
			if(categoria.getValue() == value ) {
				return categoria;
			}
		}
		
		return CategoriaEnum.DESCONHECIDO;
	}
		
}
