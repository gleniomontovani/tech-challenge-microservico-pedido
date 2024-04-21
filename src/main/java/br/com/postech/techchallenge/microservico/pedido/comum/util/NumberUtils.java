package br.com.postech.techchallenge.microservico.pedido.comum.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils {
	private static Logger logger = LoggerFactory.getLogger(NumberUtils.class);

	public static final Integer INTEGER_CEM = Integer.valueOf(100);
	
	public static BigDecimal getZeroSeNull(BigDecimal valor) {
		if (valor == null) {
			return BigDecimal.ZERO;
		}
		
		return valor;
	}
	
	public static boolean isZeroBigDecimal(BigDecimal valor) {
		BigDecimal valorDecimal = getZeroSeNull(valor);
		
		return valorDecimal.compareTo(BigDecimal.ZERO) == Constantes.INDICE_INICIAL;
	}
	
	/**
	 * Retorna um BigDecimal através do valorSTr no formato 12.208,63, por exemplo.
	 * @param valorStr
	 * @return
	 * @throws Exception
	 */
	public static BigDecimal getBigDecimal(String valorStr) {
        DecimalFormat nf = (DecimalFormat)NumberFormat.getInstance(Constantes.LOCALE_PT_BR);
        nf.setParseBigDecimal(Boolean.TRUE);

        return (BigDecimal)nf.parse(valorStr, new ParsePosition(Constantes.INDICE_INICIAL));
	}
	
	public static List<Integer> getListaInteger(String valores) {
	   List<String> listaStr = Arrays.asList(valores.split(Constantes.STRING_PONTO_VIRGULA));
	   List<Integer> lista = new ArrayList<>();
	   
	   for (String valor : listaStr) {
		   lista.add(Integer.parseInt(valor));
	   }
	   
	   return lista;
	}
   
	public static List<Long> getListaLong(String valores) {
	   List<String> listaStr = Arrays.asList(valores.split(Constantes.STRING_PONTO_VIRGULA));
	   List<Long> lista = new ArrayList<>();
	   
	   for (String valor : listaStr) {
		   lista.add(Long.parseLong(valor));
	   }
	   
	   return lista;
	}
	
	
	/**
	 * Retorna um BigDecimal com duas casas decimais, baseado no valorStr, exemplo: 1925802 retorna 19258.02
	 * @param valorStr
	 * @return
	 */
	public static BigDecimal getBigDecimalFromString(String valorStr) {
		
		BigDecimal valor = null;
		
		if (StringUtils.isNotEmpty(valorStr)) {
			valorStr = MascaraUtils.removerMascara(valorStr);
			
			if (valorStr.length() > Constantes.QTDE_PADRAO_CASAS_DECIMAIS) {
				String valorInteiro = valorStr.substring(Constantes.INDICE_INICIAL, valorStr.length() - Constantes.QTDE_PADRAO_CASAS_DECIMAIS);
				String valorFracionario = valorStr.substring(valorStr.length() - Constantes.QTDE_PADRAO_CASAS_DECIMAIS, valorStr.length());
				valor = new BigDecimal(valorInteiro.concat(Constantes.STRING_PONTO).concat(valorFracionario));
			}
		}
		
		return getZeroSeNull(valor);
	}
	
	public static BigDecimal getBigDecimalFromStringRespeitandoCasaDecimal(String valorStr) {
		
		BigDecimal valor = null;
		
		if (StringUtils.isNotEmpty(valorStr)) {

			String[] valores = valorStr.split(",");
			String valorInteiro = MascaraUtils.removerMascara(valores[0]);
			String valorFracionario;
			if (valores.length > 1) {
				valorFracionario = MascaraUtils.removerMascara(valores[1]);
			} else {
				valorFracionario = "00";
			}
			
			valor = new BigDecimal(valorInteiro.concat(Constantes.STRING_PONTO).concat(valorFracionario));
		}
		
		return getZeroSeNull(valor);
	}
	
	/**
	 * Verifica se o número é válido e maior que zero
	 * @param numero
	 * @return
	 */
	public static boolean isNumeroValido(Number numero) {
		return isNumeroValido(numero, Boolean.FALSE); 
	}
	
	/**
	 * Verifica se o número é válido, permitindo ou não ZERO de acordo com o parâmetro isZeroValido 
	 * @param numero
	 * @return
	 */
	public static boolean isNumeroValido(Number numero, boolean isZeroValido) {
		boolean isValido = Boolean.FALSE;
		
		if (numero != null) {
			if (numero instanceof Long) {
				isValido = numero.longValue() >= (isZeroValido ? Constantes.PRIMEIRO_INTEIRO_VALIDO : Constantes.PRIMEIRO_INTEIRO_DIFERENTE_ZERO);
			} else if (numero instanceof Integer) {
				isValido = numero.intValue() >= (isZeroValido ? Constantes.PRIMEIRO_INTEIRO_VALIDO : Constantes.PRIMEIRO_INTEIRO_DIFERENTE_ZERO);
			}
		}
		
		return isValido; 
	}

	/**
	 * Formata o valor em monetário R$ 9.999,99, por exemplo
	 * @param valor
	 * @return
	 */
    public static String formatCurrency(final Number valor) {
        if (valor == null) {
            return null;
        }

        NumberFormat format = NumberFormat.getCurrencyInstance(DateUtils.getLocale());
        return format.format(valor);
    }
    
    public static BigDecimal getVlrDocumento(String formattedvoucher) {
    	BigDecimal vlrDocumento = BigDecimal.ZERO;
    	if(StringUtils.isNotBlank(formattedvoucher)) {
    		String[] documentos = formattedvoucher.split("\n");
    		if((ValidacaoUtils.isPreenchido(documentos)) && documentos.length >= 13) {	
    			String vlrStrDocumento = documentos[12].replaceAll("VLR DOCUMENTO", "").trim();
    			
    			logger.info("Valor String do documento: " + vlrStrDocumento);
    			
    			vlrDocumento = MascaraUtils.recuperaStringFormataRealParaBigDecimal(vlrStrDocumento);
    		}
    	}    	
    	
    	return vlrDocumento;
    }
    
    /**
     * Formata o valor numerico com separador de milhar
     * @param
     * @return
     */
    public static String formatNumber(final Number valor) {
    	if (valor == null) {
    		return null;
    	}
    	
    	NumberFormat format = NumberFormat.getNumberInstance(DateUtils.getLocale());
    	return format.format(valor);
    }
    
    public static Integer getIntegerOuNull(String valor) {
		if (StringUtils.isNotEmpty(valor)) {
			return Integer.valueOf(valor);
		}
		
		return null;
	}
    
    public static Integer getIntegerOuZero(String valor) {
		if (StringUtils.isNotEmpty(valor)) {
			return Integer.valueOf(valor);
		}
		
		return 0;
	}
	
    public static Long getLongOuNull(String valor) {
		if (StringUtils.isNotEmpty(valor)) {
			return Long.valueOf(valor);
		}
		
		return null;
	}
	
    public static Double getDoubleOuZero(String valor) {
		if (StringUtils.isNotEmpty(valor)) {
			return Double.valueOf(valor);
		}
		
		return Double.valueOf(0);
	}
    
    public static BigDecimal getBigDecimalOuNull(String valor) {
		if (StringUtils.isNotEmpty(valor)) {
			return NumberUtils.getBigDecimal(valor);
		}
		
		return null;
	}
    
    public static BigDecimal getBigDecimalOuZero(String valor) {
		if (StringUtils.isNotEmpty(valor)) {
			return NumberUtils.getBigDecimal(valor);
		}
		
		return BigDecimal.ZERO;
	}
    
    public static String format(String valor, String formato) {
        DecimalFormat df = new DecimalFormat(formato); 
        return df.format(Long.valueOf(valor));    
    }
    
    /**
    *
    * @param valor
    * @return
    */
   public static String formatReal(Double valor) {
       DecimalFormat df = new DecimalFormat("R$ ##,###,### 0.00");
       return df.format(valor);
   }
   
   public static String formatReal(Integer valor) {
	   DecimalFormat df = new DecimalFormat("R$ ##,###,### 0.00");
	   return df.format(valor);
   }

   /**
    *
    * @param numero
    * @return
    */
   public static String formataNumero(String numero) {
       if (null != numero && !"".equals(numero)) {
    	   NumberFormat nf = NumberFormat.getInstance();
           return nf.format(Double.valueOf(numero));
       }else{
     	  numero = "0";
       }
       return numero;
   }
   
   /**
   *
   * @param numero
   * @return
   */
  public static String formataReal(Double numero) {
	  Locale localeBR = new Locale("pt","BR");
	  NumberFormat nf = NumberFormat.getCurrencyInstance(localeBR);
      if (null == numero) {    	  
    	  numero = Double.valueOf(0);
      }
      
      return nf.format(numero);
  }
  
  public static String getValorPorcentagem(Double numero){
	 if(ValidacaoUtils.isPreenchido(numero) && (!Double.isNaN(numero))){
		 double resto = numero % 2;
		 if(resto != 0){
			 return String.format("%.2f", numero).concat("%");
		 }
		 return String.valueOf(numero.intValue()).concat("%");
	 }
	 
	 return String.valueOf(0).concat("%");
  }
  
//Returns true if n is even, else odd
  public static Boolean isEven(int number) {
	//Checking if the number is divisible by 2
    return ((number % 2) == 0);
  }
  
}