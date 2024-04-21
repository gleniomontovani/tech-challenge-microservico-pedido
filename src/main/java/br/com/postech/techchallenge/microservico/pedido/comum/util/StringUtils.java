package br.com.postech.techchallenge.microservico.pedido.comum.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

	public static final String ASPAS_STR = "'%s'";
	public static final String VALUE_DESCRICAO = "%s - %s";
	
	/**
	 * Adiciona o character a direita até completar o tamanho especificado
	 * @param str
	 * @param size
	 * @param character
	 * @return
	 */
    public static String rightPad(String str, int size, String character) {
        while (str.length() < size) {
            str = str + character;
        }
        return str;
    }

    /**
     * Adiciona o character a esquerda até completar o tamanho especificado
     * @param str
     * @param size
     * @param character
     * @return
     */
    public static String leftPad(String str, int size, String character) {
        while (str.length() < size) {
            str = character + str;
        }
        return str;
    }

    /**
     * Remove a esquerda enquanto existir o character
     * @param str
     * @param character
     * @return
     */
    public static String stripLeft(String str, Character character) {
        String c = character.toString();
        while (str.startsWith(c)) {
            str = str.substring(1, str.length());
        }
        return str;
    }

    /**
     * Remove a esquerda enquanto existir o character até o tamanho especificado
     * @param str
     * @param maxLength
     * @param character
     * @return
     */
    public static String stripLeft(String str, int maxLength, Character character) {
        String c = character.toString();
        while (str.startsWith(c) && str.length() > maxLength) {
            str = str.substring(1, str.length());
        }
        return str;
    }

    /**
     * Remove a direita enquanto existir o character
     * @param str
     * @param character
     * @return
     */
    public static String stripRight(String str, Character character) {
        String c = character.toString();
        while (str.endsWith(c)) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    /**
     * Remove a direita enquanto existir o character até o tamanho especificado
     * @param str
     * @param maxLength
     * @param character
     * @return
     */
    public static String stripRight(String str, int maxLength, Character character) {
        String c = character.toString();
        while (str.endsWith(c) && str.length() > maxLength) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public static String formataEmailRecuperacao(String email){
        String a =email.split("@")[0];
        String b =email.split("@")[1];
        String c="";
        for(int i=0;i!=a.length();i++){
            if (i==0 || i==a.length()-1){
                c+= a.substring(i,i+1);
            }else {
                c+= "*";
            }
        }
        return c+"@"+b;
    }
    
    /**
     * Remove acentos da str
     * @param str
     * @return
     */
	public static String removeAcentos(String str) {
		if (str == null) {
			return Constantes.STRING_VAZIA;
		}
		
		str = Normalizer.normalize(str, Normalizer.Form.NFD);
		str = str.replaceAll("[^\\p{ASCII}]", Constantes.STRING_VAZIA).replaceAll(Constantes.STRING_ESPACO_DUPLO, Constantes.STRING_ESPACO);
		return str;
	}
	
	/**
	 * Retorna o texto entre parênteses
	 * @param texto
	 * @return
	 */
	public static String getTextoEntreParenteses(String texto) {
		return Constantes.STRING_ABRE_PARENTESE.concat(texto).concat(Constantes.STRING_FECHA_PARENTESE);
	}
	
	/**
	 * Retorna o texto entre aspas simples
	 * @param texto
	 * @return
	 */
	public static String getTextoEntreAspasSimples(String texto) {
		return Constantes.STRING_ASPA_SIMPLES.concat(texto).concat(Constantes.STRING_ASPA_SIMPLES);
	}
	
	/**
	 * Retorna o texto, caso esteja preenchido ou string vazia se null 
	 * @param texto
	 * @return
	 */
	public static String getStringVaziaSeNull(String texto) {
		return isNotEmpty(texto) ? texto : Constantes.STRING_VAZIA;
	}
	
    /**
    *
    * @param valor
    * @return
    */
   public static String formataReal(BigDecimal valor) {
	   Locale.setDefault(new Locale("pt", "BR"));  // mudança global
	   if(valor == null){
		   valor = BigDecimal.ZERO;
	   }
       DecimalFormat df = new DecimalFormat("R$ #,##0.00");
       return df.format(valor);
   }
   
   /**
    * Retorna a string com a primeira letra Maiúscula
    * @param string
    * @return
    */
   public static String getStringComPrimeiraLetraMaiuscula(String string) {
       return string.substring(0, 1).toUpperCase() + string.substring(1);
   }
   
   /**
    * Retorna uma Lista de String em função dos valores separados por ponto e vírgula (;)
    * @param valores
    * @return
    */
   public static List<String> getLista(String valores) {
	   return Arrays.asList(valores.split(Constantes.PONTO_VIRGULA));
   }
   
   /**
    * Retorna uma string, com os valores da lista separados por virgula
    * @param lista
    * @return
    */
   public static String getValoresSeparadosPorVirgula(Collection<?> lista) {
	   String valores = Constantes.STRING_VAZIA;
	   
	   if (ValidacaoUtils.isPreenchido(lista)) {
		   valores = lista.toString().replace(Constantes.STRING_ABRE_COLCHETE, Constantes.STRING_VAZIA).replace(Constantes.STRING_FECHA_COLCHETE, Constantes.STRING_VAZIA);
	   }
	   
	   return valores;
   }
   
   /**
    * Remove todos os carácteres que não são ASCII 
    * @param texto
    * @return
    */
   public static String getAsciiPrintable(String texto) {
	   StringBuilder sb = new StringBuilder();
	   
	   if (StringUtils.isNotEmpty(texto)) {
		   for (char ch : texto.toCharArray()) {
			   if (StringUtils.isAsciiPrintable(ch)) {
				   sb.append(ch);
			   }
		   }
	   }
	   
	   return sb.toString();
   }
   
   /**
    * Verifica se o texto é printable
    * @param str
    * @return
    */
   public static boolean isAsciiPrintable(String str) {
      if (str == null) {
          return false;
      }
      
      int sz = str.length();
      
      for (int i = 0; i < sz; i++) {
          if (isAsciiPrintable(str.charAt(i)) == false) {
              return false;
          }
      }
      
      return true;
   }
	  
	  /**
	   * <p>Checks whether the character is ASCII 7 bit printable.</p>
	   *
	   * <pre>
	   *   CharUtils.isAsciiPrintable('a')  = true
	   *   CharUtils.isAsciiPrintable('A')  = true
	   *   CharUtils.isAsciiPrintable('3')  = true
	   *   CharUtils.isAsciiPrintable('-')  = true
	   *   CharUtils.isAsciiPrintable('\n') = false
	   *   CharUtils.isAsciiPrintable('&copy;') = false
	   * </pre>
	   * 
	   * @param ch  the character to check
	   * @return true if between 32 and 126 inclusive
	   */
	public static boolean isAsciiPrintable(char ch) {
		return ch >= 32 && ch < 127;
	}
	
	/**
	 * Remove todas as ocorrências da regex pela replacement
	 * @param texto
	 * @param regex
	 * @param replacement
	 * @return
	 */
	public static String replaceAll(String texto, String regex, String replacement) {
		while (texto.contains(regex)) {
			texto = texto.replaceAll(regex, replacement);
		}
		
		return texto;
	}
	
	/**
	 * Retorna o cnpj base 
	 * @param cnpj
	 * @return
	 */
	public static String getCnpjBase(String cnpj) {
		String cnpjBase = Constantes.STRING_VAZIA;
		
		if (StringUtils.isNotEmpty(cnpj) && cnpj.length() >= Constantes.LENGTH_CNPJ_BASE) {
			cnpjBase = cnpj.substring(Constantes.INDICE_INICIAL, Constantes.LENGTH_CNPJ_BASE);
		}
		
		return cnpjBase;
	}
	
	/**
	 * Retorna um texto com valor - descricação, exemplo: 1 - Sim, 2 - Nâo...
	 * @param value
	 * @param descricao
	 * @return
	 */
	public static String getValueDescricao(Object value, Object descricao) {
		return String.format(VALUE_DESCRICAO, new Object[] {value, descricao}); 
	}
	
	/**
	 * Retorna o texto com valores separados por ponto e vírgula (;) substituindo por vírgula (,) para que seja possível aplicar o filtro IN
	 * @param valor
	 * @return
	 */
	public static String getValrFiltroListInString(String valor) {
		if (StringUtils.isNotEmpty(valor)) {
			return valor.replace(Constantes.PONTO_VIRGULA, Constantes.STRING_VIRGULA);
		}
		
		return null;
	}
	
	/**
	 * Retorna a string entre aspas simples
	 * @param valor
	 * @return
	 */
	public static String getStringParaSql(String valor) {
		if (StringUtils.isNotEmpty(valor)) {
			return String.format(ASPAS_STR, valor);
		}
		
		return null;
	}
	
    @SuppressWarnings("deprecation")
	public static double razaoDeSimilaridade(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) { // longer should always have greater length
            longer = s2; shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) { return 1.0; /* both strings are zero length */ }
        return (longerLength - StringUtils.getLevenshteinDistance(longer, shorter)) / (double) longerLength;
    }
    
    public static String formatDataStr(String data, String mascara, int[] positions){
    	if(ValidacaoUtils.isPreenchido(data)){
    		int position = 0;
    		int i = 0;
    		try {
    			while(i < positions.length){
    				data = data.substring(position, positions[i]).concat(mascara).concat(data.substring(positions[i], data.length()));
    				
    				position = positions[i];
    				i++;
    			}			
			} catch (Exception e) {
			}    		
    	}
    	return data;
    }

	public static String superscript(String str) {
	    str = str.replaceAll("0", "⁰");
	    str = str.replaceAll("1", "¹");
	    str = str.replaceAll("2", "²");
	    str = str.replaceAll("3", "³");
	    str = str.replaceAll("4", "⁴");
	    str = str.replaceAll("5", "⁵");
	    str = str.replaceAll("6", "⁶");
	    str = str.replaceAll("7", "⁷");
	    str = str.replaceAll("8", "⁸");
	    str = str.replaceAll("9", "⁹");         
	    return str;
	}
}
