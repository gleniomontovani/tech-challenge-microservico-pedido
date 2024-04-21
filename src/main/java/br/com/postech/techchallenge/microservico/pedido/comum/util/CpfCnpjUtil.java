package br.com.postech.techchallenge.microservico.pedido.comum.util;

import org.apache.commons.lang3.StringUtils;

//classe no padrao singleton para validar cpf e cnpj
public class CpfCnpjUtil {

    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    private static CpfCnpjUtil instancia;

    private CpfCnpjUtil(){
    }

    public static synchronized CpfCnpjUtil getInstancia(){
        if(instancia == null)
            instancia = new CpfCnpjUtil();
        return instancia;
    }

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
            digito = Integer.parseInt(str.substring(indice,indice+1));
            soma += digito*peso[peso.length-str.length()+indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    public static boolean isValidCPF(String cpf) {
        if ((cpf==null) || (cpf.length()!=11)) return false;

        Integer digito1 = calcularDigito(cpf.substring(0,9), pesoCPF);
        Integer digito2 = calcularDigito(cpf.substring(0,9) + digito1, pesoCPF);
        return cpf.equals(cpf.substring(0,9) + digito1.toString() + digito2.toString());
    }

    public static boolean isValidCNPJ(String cnpj) {
        if ((cnpj==null)||(cnpj.length()!=14)) return false;

        Integer digito1 = calcularDigito(cnpj.substring(0,12), pesoCNPJ);
        Integer digito2 = calcularDigito(cnpj.substring(0,12) + digito1, pesoCNPJ);
        return cnpj.equals(cnpj.substring(0,12) + digito1.toString() + digito2.toString());
    }
    
    public static String removeMaskCPFCNPJ(String cpfCnpj){
    	String cpfCnpjFormatado;
		if(StringUtils.isEmpty(cpfCnpj)){
			return null;
		}else{
			cpfCnpjFormatado = cpfCnpj.replaceAll("[^0-9]", "");//Retorna somente numero de uma string
		}
		
		return cpfCnpjFormatado;
    }

}
