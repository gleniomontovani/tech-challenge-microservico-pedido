package br.com.postech.techchallenge.microservico.pedido.comum.util;

import java.math.BigDecimal;
import java.util.Locale;

public class Constantes {
	public static final String SERVER_NAME								= "TECH CHALLENGE - POS-TECH";
	
	public static final Object OBJECT_NULL								= null;
		
	public static final String ENUM_PACKAGE 							= "br.com.postech.techchallenge.microservico.pedido.comum.enums.";
	public static final String ASSOCIACAO_TYPE 							= "br.com.postech.techchallenge.microservico.pedido.comum.enums.AssociacaoType";
	public static final String ENUM_CLASS_NAME 							= "enumClassName";
		
	public static final String MESSAGE_SUCCESS 							= "success";
	public static final String MESSAGE_WARNING 							= "warn";
	public static final String MESSAGE_DANGER 							= "danger";
	
	public static final String UNAUTHORIZED								= "unauthorized";
	
    public static final String CLIENTE_ID 								= "4SOAT";
    public static final String CLIENTE_CPF_1							= "27974558008";
    public static final String CLIENTE_CPF_2							= "27269298009";
    public static final String CLIENTE_EMAIL							= "example@gmail.com";
    public static final String CLIENTE_NAME								= "Tech Challenge";
    
    public static final String PRODUTO_NOME								= "Coca-Cola";
    public static final String PRODUTO_DESCRICAO						= "Refrigerante Coca-Cola";
    
    public static final String SECRET_CLIENTE_ID 						= "123321a";
    public static final String SCOPE_READ 								= "read";
    public static final String SCOPE_WRITE 								= "write";
    public static final String SCOPE_READ_DESCRIPTION					= "read all";
    public static final String SCOPE_WRITE_DESCRIPTION					= "access all";
    public static final String GRANT_TYPE 								= "password"+","+"refresh_token";
    public static final String PATH_VIEW_SWAGGER 						= "classpath:/swagger/";
    public static final String PATH_VIEW_IMAGES 						= "classpath:/images/";
    public static final String PATH_VIEW_GENERAL_SWAGGER				= "/swagger/**";
    public static final String PATH_VIEW_GENERAL_IMAGES					= "/images/**";
    public static final String PATH_VIEW_CHAT 							= "classpath:/chat/";
    public static final String PATH_VIEW_GENERAL_CHAT					= "/chat/**";
    public static final int ACCESS_TOKEN_VALIDITY 						= 3600;
    public static final int REFRESH_TOKEN_VALIDITY 						= 3600;
    public static final Long TIME_VALIDITY_CODE_ACCESS					= 600L;
    public static final String DELETAR_CONTA 							= "Sua Conta foi Deletada!!";
    public static final String STATUS_PENDING 							= "pending";
    public static final String STATUS_PAID 								= "paid";
    public static final String STATUS_CANCELED							= "canceled";
    public static final String STATUS_EXPIRED 							= "expired";
    public static final String ROLE_EMPRESA 							= "ROLE_EMPRESA";
    
    public static final String SPRING_PROFILES_ACTIVE 					= "spring.profiles.active";
	public static final String DESENVOLVIMENTO 							= "dev";	
	public static final String HOMOLOGACAO 								= "homolog";
	public static final String PRODUCAO 								= "prod";
	
    public static final Locale LOCALE_PT_BR 							= new Locale("pt", "BR");
    public static final String UTF_8 									= "UTF-8";
    public static final String CONTENT_HTML								= "text/html";
    
    public static final BigDecimal VALOR_MINIMO_OZ 						= BigDecimal.valueOf(0.50);	
    public static final BigDecimal PLANO_ASSINATURA_PARA_CARTAO			= BigDecimal.valueOf(300);
    public static final long HORARIO_PERMITIDO_PARA_CANCELAR_SEGUNDOS 	= 172800;
    public static final long HORARIO_PERMITIDO_PARA_CANCELAR_HORAS  	= 48;
    public static final double HORARIO_PERMITIDO_PARA_CANCELAR_DIAS  	= 2.0;
    
    public static final int CODE_AREA_PT_BR								= 55;
    
    public static final String SUCESS_TRANSACTION_CELCOIN_REPONSE	    = "000";
    
    public static final String FAIL_TRY_PAYMENT							= "Falha ao tentar efetuar o pagamento!";
    public static final String SUCESS_MAKE_PAYMENT						= "O pagamento foi efetuado com sucesso!";

	public static final int INT_ZERO 									= 0;
	public static final int INT_UM 										= 1;
	public static final int INT_DOIS									= 2;
	public static final int INT_TRES									= 3;
	public static final int INT_QUATRO									= 4;
	public static final int INT_CINCO									= 5;
	public static final int INT_SEIS									= 6;
	public static final int INT_SETE									= 7;
	public static final int INT_OITO									= 8;
	public static final int INT_NOVE									= 9;
	public static final int INT_QUARENTA_E_QUATRO						= 44;
	
	
	public static final long LONG_ZERO									= 0L;
	public static final long LONG_CEM									= 100L;
    
	public static final int INDEX_HORA 									= 0;
    public static final int INDICE_INICIAL 								= 0;
    public static final int INDEX_MINUTO 								= 1;
	public static final int INDEX_SEGUNDO 								= 2;
	public static final int HORA_MINUTO 								= 2;
	public static final int HORA_MINUTO_SEGUNDO 						= 3;
	
	public static final int COMPARE_TO_MAIOR 							= 1;
	public static final int COMPARE_TO_IGUAL 							= 0;
	public static final int COMPARE_TO_MENOR 							= -1;
	
	public static final int INDICE_FINAL_DDD 							= 2;	
	public static final int QTDE_DIGITOS_CPF 							= 11;
	public static final int QTDE_DIGITO_DD_MAIS_TELEFONE_PADRAO 		= 10;
    
	public static final String STRING_COPYRIGHT							= "©";
    public static final String STRING_VAZIA 							= "";
    public static final String STRING_ESPACO 							= " ";
    public static final String STRING_ZERO 								= "0";
    public static final String STRING_DOIS_PONTOS 						= ":";
    public static final String STRING_PONTO_VIRGULA 					= ";";
    public static final String STRING_PONTO 							= ".";
    public static final String STRING_BARRA 							= "/";
    public static final String STRING_ESPACO_DUPLO 						= " ";
    public static final String STRING_ABRE_PARENTESE 					= "(";
	public static final String STRING_FECHA_PARENTESE 					= ")";
	public static final String STRING_ASPA 								= "\"";
	public static final String STRING_ASPA_SIMPLES 						= "'";
	public static final String PONTO_VIRGULA 							= ";";
	public static final String STRING_ABRE_COLCHETE 					= "[";
	public static final String STRING_FECHA_COLCHETE 					= "]";
	public static final String STRING_VIRGULA 							= ",";
	public static final int LENGTH_CNPJ_BASE 							= 8;
    
    public static final int QTDE_PADRAO_CASAS_DECIMAIS 					= 2;
	public static final int PRIMEIRO_INTEIRO_VALIDO 					= 0;
	public static final int PRIMEIRO_INTEIRO_DIFERENTE_ZERO 			= 1;
	
	public static final int QTDE_CONVITE_ACEITO_INSENCAO_PGT_CARD 		= 5;
	
}
