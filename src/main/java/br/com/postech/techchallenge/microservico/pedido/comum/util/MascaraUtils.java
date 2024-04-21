package br.com.postech.techchallenge.microservico.pedido.comum.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.swing.text.MaskFormatter;

import org.apache.commons.lang3.StringUtils;

public class MascaraUtils {
	
	private static final String DD_NNNN_NNNN = "(##) ####-####";
	private static final String DD_NNNNN_NNNN = "(##) #####-####";
	private static final String DD_NNNN_NNNN_SEM_PARENTESES = "## ####-####";
	private static final String DD_NNNNN_NNNN_SEM_PARENTESES = "## #####-####";
	public static final String CPF = "###.###.###-##";
	public static final String CNPJ = "##.###.###/####-##";
	public static final String CNPJ_BASE = "##.###.###";
	public static final String INSCRICAO_ESTADUAL = "##.###.###-#";
	public static final String CNAE = "####-#/##";
	public static final String CEP = "##.###-###";
	

	
	/**
	 * Formata o telefone nas mascaras (##) ####-#### ou (##) #####-####
	 * @param telefone
	 * @return
	 */
	public static String formatarTelefone(String telefone) {
		if (StringUtils.isNotEmpty(telefone)) {
			String mascara = telefone.length() == Constantes.QTDE_DIGITO_DD_MAIS_TELEFONE_PADRAO ? DD_NNNN_NNNN : DD_NNNNN_NNNN;
			return formatarTelefone(telefone, mascara);
		}
		
		return telefone;
	}
	
	/**
	 * Formata o telefone nas mascaras ## ####-#### ou ## #####-####
	 * @param telefone
	 * @return
	 */
	public static String formatarTelefoneSemParenteses(String telefone) {
		if (StringUtils.isNotEmpty(telefone)) {
			String mascara = telefone.length() == Constantes.QTDE_DIGITO_DD_MAIS_TELEFONE_PADRAO ? DD_NNNN_NNNN_SEM_PARENTESES : DD_NNNNN_NNNN_SEM_PARENTESES;
			return formatarTelefone(telefone, mascara);
		}
		
		return telefone;
	}

	/**
	 * Formata o telefone de acordo com a mascara informada
	 * @param telefone
	 * @return
	 */
	public static String formatarTelefone(String telefone, String mascara) {
        try {
        	MaskFormatter mf = new MaskFormatter(mascara);
            mf.setValueContainsLiteralCharacters(false);
            return mf.valueToString(telefone);
        } catch (ParseException ex) {
            return telefone;
        }
    }
	
	public static String formatarNumeroDecimal(BigDecimal numero, String mascara){
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
		DecimalFormat df = (DecimalFormat)nf;
		
		if (StringUtils.isNotBlank(mascara)){
			df.applyPattern(mascara);
		} else {
			df.applyPattern("###,###.##");
		}
		
		return df.format(numero);
	}
	
	public static String formatarValoresParaReal(BigDecimal numero) {
		Locale brazilLocale = new Locale("pt", "BR");
		NumberFormat nf = NumberFormat.getCurrencyInstance(brazilLocale);  
		String valorConvertido = nf.format(numero);
		return valorConvertido.replace("R$", "");
	}
	
	public static BigDecimal recuperaStringFormataRealParaBigDecimal(String str){
		str = str.replace("R$", "");
        str = str.replace(".","");
        str = str.replace(",", ".");
        str = str.trim();
        return new BigDecimal(str);
	}
	
	public static String formatarNumeroDecimal(BigDecimal numero){
		return formatarNumeroDecimal(numero, null);
	}
	
	public static String formatarNumeroInteiro(Long numero){
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
		
		return nf.format(numero);
	}
	
	public static String removeUnderscoreAndCapitalize(String stringParaFormatar) {
		String removidoUnderScore = stringParaFormatar.replace("_", " ");
		String minuscula = removidoUnderScore.toLowerCase();
		return StringUtils.capitalize( minuscula );
	} 
	
	/**
	 * Remove a mascara da str e retorna apenas os números
	 * @param str
	 * @return
	 */
	public static String removerMascara(String str){
		if(ValidacaoUtils.isPreenchido(str)) {
			return str.replaceAll("\\D", "");
		}
		
		return null;
    }
	
	public static String removerMascaraSexo(String campo){
        String novoCampo = "";
        if(ValidacaoUtils.isPreenchido(campo)) {
	        if(campo.startsWith("F") || campo.startsWith("f") ){
	             novoCampo = "Feminino";
	        }
	
	        if(campo.startsWith("M") || campo.startsWith("m") ){
	            novoCampo = "Masculino";
	        }
	
	        if( campo.startsWith("O") || campo.startsWith("o") ){
	            novoCampo = "Outro";
	        }
        }
        return novoCampo;
    }
	
	/**
	 * Retonra o ddd do telefone completo, independente se possui mascara ou não
	 * @param telefoneCompleto
	 * @return
	 */
	public static String getDddDoTelefone(String telefoneCompleto) {
		String telefoneSemMascara = MascaraUtils.removerMascara(telefoneCompleto);
		return telefoneSemMascara.substring(Constantes.INDICE_INICIAL, Constantes.INDICE_FINAL_DDD);
	}
	
	/**
	 * Retorna o número do telefone completo, independente se possui mascara ou não
	 * @param telefoneCompleto
	 * @return
	 */
	public static String getNumeroDoTelefone(String telefoneCompleto) {
		String telefoneSemMascara = MascaraUtils.removerMascara(telefoneCompleto);
		return telefoneSemMascara.substring(Constantes.INDICE_FINAL_DDD, telefoneSemMascara.length());
	}
	
	/**
	 * Retorna o inscrição estadual formatada
	 * @param inscrição estadual
	 * @return
	 */
	public static String getInscricaoEstadualFormatada(String inscricao) {
		return formatar(inscricao, INSCRICAO_ESTADUAL);
    }
	
	/**
	 * 
	 * @param cpfOuCnpj
	 * @return
	 */
	public static String getCpfOuCnpjFormatado(String cpfOuCnpj) {
		String cpfOuCnpjFormatado = Constantes.STRING_VAZIA;
		
		if (StringUtils.isNotEmpty(cpfOuCnpj)) {
			cpfOuCnpjFormatado = cpfOuCnpj.length() == Constantes.QTDE_DIGITOS_CPF ? getCpfFormatado(cpfOuCnpj) : getCnpjFormatado(cpfOuCnpj);
		}
		
		return cpfOuCnpjFormatado; 
	}
	
	/**
	 * Retorna o cnpj base formatado
	 * @param cnpjBase
	 * @return
	 */
	public static String getCnpjBaseFormatado(String cnpjBase) {
		return formatar(cnpjBase, CNPJ_BASE);
    }
	
	/**
	 * Retorna o cnpj formatado
	 * @param cnpj
	 * @return
	 */
	public static String getCnpjFormatado(String cnpj) {
		return formatar(cnpj, CNPJ); 
	}
	
	/**
	 * Retorna o cpf formatado
	 * @param cpf
	 * @return
	 */
	public static String getCpfFormatado(String cpf) {
		return formatar(cpf, CPF); 
	}
	
	/**
	 * Retorna o CNAE formatado
	 * @param cnae
	 * @return
	 */
	public static String getCnaeFormatado(String cnae) {
		return formatar(cnae, CNAE); 
	
	}
	/**
	 * Retorna o CEP formatado
	 * @param cep
	 * @return
	 */
	public static String getCepFormatado(String cep) {
		return formatar(cep, CEP); 
	}
	
	/**
	 * Retorna o valor formatado conforme a mascara
	 * @param valor
	 * @param mascara
	 * @return
	 */
	public static String formatar(String valor, String mascara) {
		try {
			if (StringUtils.isNotEmpty(valor)) {
				MaskFormatter mf = new MaskFormatter(mascara);
				mf.setValueContainsLiteralCharacters(false);
				return mf.valueToString(valor);
			} else {
				return valor;
			}
		} catch (ParseException ex) {
			return valor;
		}
	}
}
