package br.com.postech.techchallenge.microservico.pedido.comum.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

public class ValidacaoUtils {
	
	public static boolean isPreenchido(Object valor) {
		
		if (valor != null) {
			if (valor instanceof Collection<?>) {
				return ((Collection<?>)valor).size() > Constantes.INDICE_INICIAL;
			} else if (valor instanceof String) {
				return StringUtils.isNotEmpty(String.valueOf(valor)); 			
			} else if (valor instanceof Integer) {
				return (valor != null && (Integer) valor > 0); 
			} else if (valor instanceof BigDecimal){
				return ((BigDecimal) valor).compareTo(BigDecimal.ZERO) == 1;
			} else if (valor instanceof Serializable){
				return valor.equals(valor);
			}
			
			return Boolean.TRUE;
		}
		
		return Boolean.FALSE;
	}
	
	public static boolean isNaoPreenchido(Object valor) {
		return !isPreenchido(valor);
	}
	
	public static boolean isListaComApenasUmItem(Collection<?> lista) {
		return isPreenchido(lista) && lista.size() == NumberUtils.INTEGER_ONE;
	}
	
	public static boolean isListaComMaisDeUmItem(Collection<?> lista) {
		return isPreenchido(lista) && lista.size() > NumberUtils.INTEGER_ONE;
	}
	
	public static boolean initializeCollection(Collection<?> collection) {
	    // works with Hibernate EM 3.6.1-SNAPSHOT
	    if(collection == null) {
	        return Boolean.FALSE;
	    }
	    
	    return collection.iterator().hasNext();
	}
}
