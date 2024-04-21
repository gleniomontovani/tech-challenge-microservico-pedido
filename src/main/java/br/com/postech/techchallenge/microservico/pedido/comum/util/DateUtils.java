package br.com.postech.techchallenge.microservico.pedido.comum.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static String HORA_MINUTO_SEGUNDO_ZERADO = "00:00:00";
	private static Integer QUANTIDADE_SEGUNDOS_EM_UMA_HORA = 3600;
	private static Integer QUANTIDADE_SEGUNDOS_EM_UM_MINUTO = 60;
	private static Integer QUANTIDADE_MINUTOS_EM_UMA_HORA = 60;
	
	private static final String DATE_STR_PARA_SQL = "TO_DATE('%s', 'DD/MM/YYYY')";
	public static final String DD_MM_YYYY = "dd/MM/yyyy";
	public static final String YYYYMMDD = "yyyyMMdd";
	public static final String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";
	public static final String DD_MM_YYYY_HH_MM = "dd/MM/yyyy HH:mm";
	public static final String MM_YYYY = "MM/yyyy";
	public static final String YYYYMM = "yyyyMM";
	public static final String HH_MM = "HH:mm";
	public static final String HH_MM_SS = "HH:mm:ss";
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String EEE_MMM__D__HH_MM_SS__ZZZ__YYYY = "EEE MMM d HH:mm:ss zzz yyyy";

	public static final String PERIODO_STR = "%s a %s";
	
	public static Integer ULTIMA_HORA_DO_DIA = 23;
	public static Integer ULTIMO_MINUTO_DA_HORA = 59;
	public static Integer ULTIMO_SEGUNDO_DO_MINUTO = 59;
	
	public static Integer PRIMEIRA_HORA_DO_DIA = 0;
	public static Integer PRIMEIRA_MINUTO_DA_HORA = 0;
	public static Integer PRIMEIRA_SEGUNDO_DO_MINUTO = 0;
	
    public static LocalTime converterStringParaHorario(String horario){
        return LocalTime.parse(horario,DateTimeFormatter.ofPattern(HH_MM));
    }
    
    public static boolean verificarHorario(LocalTime horarioInicio,LocalTime horarioFim){
        return horarioInicio.isAfter(horarioFim);
    }

    public static Date converterHorarioParaData(LocalTime horario){
        return Date.from( horario.atDate( ( LocalDate.now() ) ).atZone(ZoneId.systemDefault()).toInstant() );
    }
    
    public static LocalDate dateParaLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
     }

    public static String convertDateHorasToString(Date date){
        return new SimpleDateFormat(HH_MM).format(date);
    }
    
    public static String convertDataToStringUSA(Date date){
        return new SimpleDateFormat(YYYY_MM_DD).format(date);
    }

    public static String convertDataToString(Date date){
        return new SimpleDateFormat(DD_MM_YYYY).format(date);
    }
    
    public static Date converterStringToDate(String data) throws ParseException {
        return new SimpleDateFormat(DD_MM_YYYY).parse(data);
    }
    
    public static double diferencaEmDiasEntreDatas(Date dataInicial, Date dataFinal) {
		double result;
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String dataInicio;
        String dataFim;
        try {
            dataInicio = df.format(dataInicial);
            dataFim = df.format(dataFinal);

            dataInicial = df.parse(dataInicio);
            dataFinal = df.parse(dataFim);

        } catch (ParseException ex) {
        	ex.printStackTrace();
        }

        long diferenca = dataFinal.getTime() - dataInicial.getTime();
        result = (diferenca / 1000) / 60 / 60 / 24; //resultado é diferença entre as datas em dias

        return result;
	}
	
    public static long diferencaEmHorasEntreDatas(Date dataInicial, Date dataFinal) {
    	long result;
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String dataInicio;
		String dataFim;
		try {
			dataInicio = df.format(dataInicial);
			dataFim = df.format(dataFinal);
			dataInicial = df.parse(dataInicio);
			dataFinal = df.parse(dataFim);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}

		long diferenca = dataFinal.getTime() - dataInicial.getTime();
		result = (diferenca / 1000) / 60 / 60; //resultado é diferença entre as datas em horas

		return result;
	}

    public static long diferencaEmMinutosEntreDatas(Date dataInicial, Date dataFinal) {
    	long result;
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dataInicio;
        String dataFim;
        try {
            dataInicio = df.format(dataInicial);
            dataFim = df.format(dataFinal);

            dataInicial = df.parse(dataInicio);
            dataFinal = df.parse(dataFim);

        } catch (ParseException ex) {
        	ex.printStackTrace();
        }

        long diferenca = dataFinal.getTime() - dataInicial.getTime();
        result = (diferenca / 1000) / 60 ; //resultado é diferença entre as datas em minutos

        return result;
	}
	    
    public static long diferencaEmSegundoEntreDatas(Date dataInicial, Date dataFinal) {  	
		long result;
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String dataInicio;
		String dataFim;
		try {
			dataInicio = df.format(dataInicial);
			dataFim = df.format(dataFinal);
			dataInicial = df.parse(dataInicio);
			dataFinal = df.parse(dataFim);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}

		long diferenca = dataFinal.getTime() - dataInicial.getTime();
		result = (diferenca / 1000); //resultado é diferença entre as datas em segundos
		
		return result;
	}
    
    public static long diferencaEmHoraEntreDataComMascara(Date dataInicial, Date dataFinal, String mascara) {  	
		long result;
		SimpleDateFormat df = new SimpleDateFormat(mascara);
		String dataInicio;
		String dataFim;
		try {
			dataInicio = df.format(dataInicial);
			dataFim = df.format(dataFinal);
			dataInicial = df.parse(dataInicio);
			dataFinal = df.parse(dataFim);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}

		long diferenca = dataFinal.getTime() - dataInicial.getTime();
		result = (diferenca / 1000); //resultado é diferença entre as datas em segundos
		
		return result;
	}
	
	/**
	 * Retorna uma instância de Locale pt-BR
	 * 
	 * @return
	 */
	public static Locale getLocale() {
		return new Locale("pt", "BR");
	}

	/**
	 * Obtem o Ano atual
	 * 
	 * @return
	 */
	public static Integer getAnoAtual() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	public static String getDataAtualFormatada() {
		return formatterDateToString(new Date());
	}
	
	public static String getDataAtualCompletaFormatada() {
		return getDataFormatada(new Date(), DD_MM_YYYY_HH_MM_SS);
	}

	/**
	 * Retorno o tempo decorrido desde o inicio até o presente momento no formato hh:mm:ss
	 * @param inicio
	 * @return
	 */
	public static String getTempoDecorido(long inicio) {
		return getTempoDecorido(inicio, System.currentTimeMillis());
	}

	/**
	 * Retorno o tempo decorrido desde o inicio até o fim no formato hh:mm:ss
	 * @param inicio
	 * @return
	 */
	public static String getTempoDecorido(long inicio, long fim) {
		long result = fim - inicio;
		long segundos = (result / 1000) % 60; // se não precisar de segundos,
												// basta remover esta linha.
		long minutos = (result / 60000) % 60; // 60000 = 60 * 1000
		long horas = result / 3600000; // 3600000 = 60 * 60 * 1000
		return String.format("%02d:%02d:%02d", horas, minutos, segundos);
	}

	public static String diferencaDoisPeriodoEmMinutoSegundo(Date inicial, Date fim) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dataInicio;
        String dataFim;
        try {
            dataInicio = df.format(inicial);
            dataFim = df.format(fim);

            inicial = df.parse(dataInicio);
            fim = df.parse(dataFim);

        } catch (ParseException ex) {
        	ex.printStackTrace();
        }

        long diferenca = fim.getTime() - inicial.getTime();
        long minuto = (diferenca / 1000) / 60 ; //resultado é diferença entre as datas em minutos
        int segundo = (int)((double)((double) diferenca / (double)1000) % (double) 60); //resultado é diferença entre as datas em segundos
		
        minuto = minuto < 0 ? 0 : minuto;
        segundo = segundo < 0 ? 0 : segundo;
        
        String duracao = String.format("%02d:%02d", minuto, segundo);//Formatando para MM:SS
        
//        logger.info("Duracao: "+duracao);
        
		return duracao;
	}
	
	public static Integer compareToTwoData(Date inicio, Date fim) {
		// 0 - For when they are the same
		// < 0 - For case date inicio less than fim
		// > 0 - For case date inicio greater than fim
		return inicio.compareTo(fim);
	}

	public static Date formatterEndDate(String dataFinal) {
		Date date = null;
		if ((dataFinal != null) && (!(dataFinal.isEmpty()))) {
			try {
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				date = (Date) format.parse(dataFinal);

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return date;
	}

	public static Date formatterStartDate(String dataInicial) {
		Date date = null;
		if ((dataInicial != null) && (!(dataInicial.isEmpty()))) {
			try {
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				date = (Date) format.parse(dataInicial);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}
	
	public static String restoreDateFormatterToString(Date data, String mask) {
		if (data == null) {
			return "";
		}
		
		SimpleDateFormat format = new SimpleDateFormat(mask);
		return format.format(data);
	}

	public static String restoreDateFormatterToString(Date data) {
		return restoreDateFormatterToString(data, "dd/MM/yyyy");
	}

	public static String formatterDateToString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		return format.format(date);
	}

	public static Date formatterStringToDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Date formatterStringToDateHour(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static String formatterStringToHourStr(String date) {
		StringBuilder horaNova = new StringBuilder();
		if(StringUtils.isNotEmpty(date)){
			String[] dateSplit = date.split(":");
			int tamanhoHora = dateSplit.length;
			int i = 1;
			for(String hora : dateSplit){
				if(hora.length() == 1){
					horaNova.append("0"+hora);					
				}else{
					horaNova.append(hora);
				}
				if(i < tamanhoHora){horaNova.append(":");}
				
				i++;
			}
		}

		return horaNova.toString();
	}
	
	public static Date sumDayInDate(Integer diasProximoVencimento) {
		try {
			SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			Calendar c = new GregorianCalendar();
			c.add(Calendar.DAY_OF_MONTH, diasProximoVencimento);
			return sd.parse(sd.format(c.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Date addDaysToDate( Integer days ) {		
		LocalDate dateAddDay =  LocalDate.now().plusDays( days );
		
		return java.util.Date.from(dateAddDay.atStartOfDay()
			      .atZone(ZoneId.systemDefault())
			      .toInstant());		
	}
	
	/**
	 * Adiciona horas e minutos a dataStr
	 * @param dataStr
	 * @param horas
	 * @param minutos
	 * @return
	 */
	public static Date getData(String dataStr, int horas, int minutos) {
		return getData(getData(dataStr), horas, minutos, NumberUtils.INTEGER_ZERO);
	}
	
	/**
	 * Adiciona horas, minutos e segundos a dataStr
	 * @param dataStr
	 * @param horas
	 * @param minutos
	 * @param segundos
	 * @return
	 */
	public static Date getData(String dataStr, int horas, int minutos, int segundos) {
		return getData(getData(dataStr), horas, minutos, segundos);
	}
	
	public static Date getData(Date data, int dia, int horas, int minutos, int segundos) {    
		final Calendar myCalendar = Calendar.getInstance();
	    myCalendar.setTime(data);
	    int diaDoMes = myCalendar.get(Calendar.DAY_OF_MONTH) + dia;
	    myCalendar.set(Calendar.DAY_OF_MONTH, diaDoMes);
	    myCalendar.set(Calendar.HOUR_OF_DAY, horas);
	    myCalendar.set(Calendar.MINUTE, minutos);
	    myCalendar.set(Calendar.SECOND, segundos);
	    myCalendar.set(Calendar.MILLISECOND, 0);
	    return myCalendar.getTime();
	}
    
    /**
     * Adiciona horas e minutos a data
     * @param data
     * @param horas
     * @param minutos
     * @return
     */
    public static Date getData(Date data, int horas, int minutos, int segundos) {       
    	final Calendar myCalendar = Calendar.getInstance();
    	myCalendar.setTime(data);
    	myCalendar.set(Calendar.HOUR_OF_DAY, horas);
    	myCalendar.set(Calendar.MINUTE, minutos);
    	myCalendar.set(Calendar.SECOND, segundos);
        myCalendar.set(Calendar.MILLISECOND, 0);
    	return myCalendar.getTime();
    }
    
    public static Date getDataNoPrimeiroSegundoDoDia(String dataStr) { 
    	return getDataNoPrimeiroSegundoDoDia(getData(dataStr));
    }
    
    /**
     * Retorna a data completa as 23:59:59
     * @param dataStr
     * @return
     */
    public static Date getDataNoUltimoSegundoDoDia(String dataStr) { 
    	return getDataNoUltimoSegundoDoDia(getData(dataStr));
    }
    
    
    /**
     * Retorna a data completa as 00:00:00
     * @return
     */
    public static Date getDataNoPrimeiroSegundoDoDia(Date data) {       
    	final Calendar myCalendar = Calendar.getInstance();
    	myCalendar.setTime(data);
    	myCalendar.set(Calendar.HOUR_OF_DAY, PRIMEIRA_HORA_DO_DIA);
    	myCalendar.set(Calendar.MINUTE, PRIMEIRA_MINUTO_DA_HORA);
    	myCalendar.set(Calendar.SECOND, PRIMEIRA_SEGUNDO_DO_MINUTO);
    	myCalendar.set(Calendar.MILLISECOND, 0);
    	return myCalendar.getTime();
    }
    
    /**
     * Retorna a data completa as 23:59:59
     * @return
     */
    public static Date getDataNoUltimoSegundoDoDia(Date data) {       
    	final Calendar myCalendar = Calendar.getInstance();
    	myCalendar.setTime(data);
    	myCalendar.set(Calendar.HOUR_OF_DAY, ULTIMA_HORA_DO_DIA);
    	myCalendar.set(Calendar.MINUTE, ULTIMO_MINUTO_DA_HORA);
    	myCalendar.set(Calendar.SECOND, ULTIMO_SEGUNDO_DO_MINUTO);
    	myCalendar.set(Calendar.MILLISECOND, 59);
    	return myCalendar.getTime();
    }
    
    public static Date getDataDiasAnterior(Date data, Integer numeroDeDias) {
    	Calendar calendario = Calendar.getInstance();
	    calendario.setTime(data);
	    int diaDoMes = calendario.get(Calendar.DAY_OF_MONTH) - numeroDeDias;
	    calendario.set(Calendar.DAY_OF_MONTH, diaDoMes);
	    
	    return calendario.getTime();
    }

    /**
     * Retorna um date de acordo com o parametro dataStr
     * @param dataStr
     * @return
     */
	public static Date getData(String dataStr) {
		Date data = null;
		
		SimpleDateFormat formatter = new SimpleDateFormat(DD_MM_YYYY);
		try {
			return formatter.parse(dataStr);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
		return data;
	}

	/**
	 * Retorna da data formatada no formato DD/MM/YYYY
	 * @param date
	 * @return
	 */
	public static String getDataFormatada(LocalDateTime date) {
		return getDataFormatada(date, DD_MM_YYYY);
	}
	
	/**
	 * Retorna da data formatada no formato DD/MM/YYYY
	 * @param date
	 * @return
	 */
	public static String getDataFormatada(LocalDate date) {
		return getDataFormatada(date, DD_MM_YYYY);
	}
	
	/**
	 * Retorna da data formatada no formato DD/MM/YYYY
	 * @param date
	 * @return
	 */
	public static String getDataFormatada(Date date) {
		return getDataFormatada(date, DD_MM_YYYY);
	}
	
	/**
	 * Retorna da data formatada no formato DD/MM/YYYY HH24:MM:SS
	 * @param date
	 * @return
	 */
	public static String getDataCompletaFormatada(Date date) {
		String dataCompletaFormatada = Constantes.STRING_VAZIA;
		
		if (date != null) {
			if (HORA_MINUTO_SEGUNDO_ZERADO.equals(getHoraCompletaFormatada(date))) {
				dataCompletaFormatada = getDataFormatada(date);
			} else {
				dataCompletaFormatada = getDataFormatada(date, DD_MM_YYYY_HH_MM_SS);
			}
		}
		
		return dataCompletaFormatada;
	}

	/**
	 * Retorna a hora no formato HH:mm
	 * @param 
	 * @return
	 */
	public static String getHoraFormatada(Date date) {
		return getDataFormatada(date, HH_MM);
	}
	
	/**
	 * Retorna a hora no formato HH:mm:ss
	 * @param 
	 * @return
	 */
	public static String getHoraCompletaFormatada(Date date) {
		return getDataFormatada(date, HH_MM_SS);
	}
	
	/**
	 * Recebe a quantidade de segundos e retorna a hora no formato HH:mm:ss
	 * <br/>
	 * <i>Exemplo:</i>
	 * <br/>	 * Recebe: 135
	 * <br/>	 * Retorna: "00:02:15"
	 * @param segundosTotais
	 * @return tempo formatado
	 */
	public static String getHoraFormatada(Integer segundosTotais){
    	String tempoFormatado = HORA_MINUTO_SEGUNDO_ZERADO;
		
		if (segundosTotais != null && segundosTotais > 0){
			Integer minutosCalculado = segundosTotais / QUANTIDADE_SEGUNDOS_EM_UM_MINUTO;
			Integer segundosCalculado = segundosTotais - (minutosCalculado * QUANTIDADE_SEGUNDOS_EM_UM_MINUTO);
			
			Integer horasCalculada = minutosCalculado / QUANTIDADE_MINUTOS_EM_UMA_HORA;
			minutosCalculado = minutosCalculado - (horasCalculada * QUANTIDADE_MINUTOS_EM_UMA_HORA);
			
			String horasString = StringUtils.leftPad(String.valueOf(horasCalculada), 2, Constantes.STRING_ZERO);
			String minutosString = StringUtils.leftPad(String.valueOf(minutosCalculado), 2, Constantes.STRING_ZERO);
			String segundosString = StringUtils.leftPad(String.valueOf(segundosCalculado), 2, Constantes.STRING_ZERO);
			
			tempoFormatado = horasString.concat(Constantes.STRING_DOIS_PONTOS).concat(minutosString).concat(Constantes.STRING_DOIS_PONTOS).concat(segundosString);
		}
		
		return tempoFormatado;
    }
	
	/**
	 * Recebe a hora no formato HH:mm:ss e retorna quantos segundos tem naquele horario, desde o começo do dia
	 * <br/>
	 * <i>Exemplo:</i>
	 * <br/>	 * Recebe: "00:02:15"
	 * <br/>	 * Retorna: 135
	 * @param horaFormatada
	 * @return segundos totais
	 */
	public static Integer getSegundos(String horaFormatada){
		if (StringUtils.isBlank(horaFormatada)){
			return 0;
		}
		
		Integer hora = Integer.valueOf(horaFormatada.split(":")[0]);
		Integer minuto = Integer.valueOf(horaFormatada.split(":")[1]);
		Integer segundo = Integer.valueOf(horaFormatada.split(":")[2]);
		
		Integer segundosTotais = 0;
		segundosTotais = segundosTotais + (hora * QUANTIDADE_SEGUNDOS_EM_UMA_HORA);
		segundosTotais = segundosTotais + (minuto * QUANTIDADE_SEGUNDOS_EM_UM_MINUTO);
		segundosTotais = segundosTotais + segundo;
		
		return segundosTotais;
	}
	
	/**
	 * Recebe a hora no formato HH:mm:ss OU HH:mm e retorna quantos minutos tem naquele horario, desde o começo do dia, desconsiderando os segundos
	 * <br/>
	 * <i>Exemplo:</i>
	 * <br/>	 * Recebe: "06:00:0"
	 * <br/>	 * Retorna: 360
	 * @param horaFormatada
	 * @return minutos totais
	 */
	public static Integer getMinutos(String horaFormatada){
		if (StringUtils.isBlank(horaFormatada)){
			return 0;
		}
		
		Integer hora = Integer.valueOf(horaFormatada.split(":")[0]);
		Integer minuto = Integer.valueOf(horaFormatada.split(":")[1]);
		
		Integer minutosTotais = 0;
		minutosTotais = minutosTotais + (hora * QUANTIDADE_MINUTOS_EM_UMA_HORA);
		minutosTotais = minutosTotais + minuto;
		
		return minutosTotais;
	}
	
	/**
	 * Retorna da data formatada de acordo com a mascara 
	 * @param dateTime
	 * @param mascara
	 * @return
	 */
	public static String getDataFormatada(LocalDateTime dateTime, String mascara) {
		String dataFormatada = Constantes.STRING_VAZIA;
		
		if (dateTime != null) {
			dataFormatada = dateTime.format(DateTimeFormatter.ofPattern(mascara, DateUtils.getLocale()));
		}
		
		return dataFormatada;
	}
	
	/**
	 * Retorna da data formatada de acordo com a mascara 
	 * @param date
	 * @param mascara
	 * @return
	 */
	public static String getDataFormatada(LocalDate date, String mascara) {
		String dataFormatada = Constantes.STRING_VAZIA;
		
		if (date != null) {
			dataFormatada = date.format(DateTimeFormatter.ofPattern(mascara, DateUtils.getLocale()));
		}
		
		return dataFormatada;
	}
	
	/**
	 * Retorna da data formatada de acordo com a mascara 
	 * @param date
	 * @param mascara
	 * @return
	 */
	public static String getDataFormatada(Date date, String mascara) {
		if(ValidacaoUtils.isPreenchido(date)){
			return new SimpleDateFormat(mascara).format(date);
		}
		
		return null;
	}
    
	/**
	 * Retorna a hora passada por parametro no formato hh:mn
	 * @param horasMinutos
	 * @return
	 */
	public static Integer getHora(String horasMinutos) {
		Integer hora = null;
		
		if (isHoraMinutoValido(horasMinutos)) {
			hora = Integer.parseInt(horasMinutos.split(Constantes.STRING_DOIS_PONTOS)[Constantes.INDEX_HORA]);
		}
		
		return hora;
	}

	/**
	 * Retorna o minuto passado por parametro no formato hh:mn
	 * @param horasMinutos
	 * @return
	 */
	public static Integer getMinuto(String horasMinutos) {
		Integer hora = null;
		
		if (isHoraMinutoValido(horasMinutos)) {
			hora = Integer.parseInt(horasMinutos.split(Constantes.STRING_DOIS_PONTOS)[Constantes.INDEX_MINUTO]);
		}
		
		return hora;
	}
	
	/**
	 * Retorna o segundo passado por parametro no formato hh:mn
	 * @param horasMinutos
	 * @return
	 */
	public static Integer getSegundo(String horasMinutosSegundos) {
		Integer segundo = null;
		
		if (isHoraMinutoValido(horasMinutosSegundos) && horasMinutosSegundos.split(Constantes.STRING_DOIS_PONTOS).length == Constantes.HORA_MINUTO_SEGUNDO) {
			segundo = Integer.parseInt(horasMinutosSegundos.split(Constantes.STRING_DOIS_PONTOS)[Constantes.INDEX_SEGUNDO]);
		}
		
		return segundo;
	}
	
	private static boolean isHoraMinutoValido(String horasMinutos) {
		return ValidacaoUtils.isPreenchido(horasMinutos) && horasMinutos.contains(Constantes.STRING_DOIS_PONTOS) && 
				horasMinutos.split(Constantes.STRING_DOIS_PONTOS).length >= Constantes.HORA_MINUTO;
	}
	
    /**
     * Retorna um date de acordo com o parametro dataStr e o formato de origem
     * @param dataStr
     * @param formato
     * @return
     */
	public static Date getData(String dataStr, String formato) {
		return getData(dataStr, formato, null);
	}

	public static Date getDateFormat(Date data, String formato) {
		return getData(getDataFormatada(data, formato), formato);
	}
	
	public static String createMaskToDateStr(String data){
		return data.substring(4).concat("/").concat(data.substring(0,4));
	}
	
	/**
	 * Retorna um date de acordo com o parametro dataStr e o formato de origem
	 * @param dataStr
	 * @param formato
	 * @return
	 */
	public static Date getData(String dataStr, String formato, Locale locale) {
		Date data = null;

		try {
			SimpleDateFormat formatter = null;
			if(ValidacaoUtils.isPreenchido(dataStr)) {
				if (locale == null) {
					formatter = new SimpleDateFormat(formato);
				} else {
					formatter = new SimpleDateFormat(formato, locale);
				}
				
				return formatter.parse(dataStr);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
		return data;
	}
	
	public static String formatDataStrToDataStrLang(String dataStr){
		String dataNova = "";
        if (StringUtils.isNotEmpty(dataStr)) {
            try {
                DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dataFormatada = df2.parse(dataStr);
                dataNova = df1.format(dataFormatada);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        return dataNova;
	}
	
	public static String formatDateStrToDateByFormat(String dataStr, String formatOriginal, String formatNew){
		String dataNovaStr = "";
        if (StringUtils.isNotEmpty(dataStr)) {
            try {
                DateFormat df1 = new SimpleDateFormat(formatNew);
                DateFormat df2 = new SimpleDateFormat(formatOriginal);
                Date dataFormatada = df2.parse(dataStr);
                dataNovaStr = df1.format(dataFormatada);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        return dataNovaStr;
	}
	
	public static String formatDataStrToDataStrWithMask(String dataStr){
		String dataNova = "";
        if (StringUtils.isNotEmpty(dataStr)) {
            try {
                DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dataFormatada = df2.parse(dataStr);
                dataNova = df1.format(dataFormatada);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        return dataNova;
	}
	
	/**
	 * Verifica se a data é maior que atual, desconsiderando horas, minutos, segundos... 
	 * @param data
	 * @return
	 */
	public static boolean isDataMaiorQueAtual(Date data) {
		return getData(data).after(getData(new Date()));
	}
	
	/**
	 * Verifica se a data é menor que atual, desconsiderando horas, minutos, segundos... 
	 * @param data
	 * @return
	 */
	public static boolean isDataMenorQueAtual(Date data) {
		return getData(data).before(getData(new Date()));
	}
	
    /**
     * Verifica se a data1 é MAIOR ou IGUAL a data2, desconsiderando horas, minutos, segundos... 
     * @param data1
     * @param data2
     * @return
     */
    public static boolean isDataMaiorOuIgualQue(Date data1, Date data2) {
    	return isDataMaiorQue(data1, data2) || isDatasIguais(data1, data2);
    }
    
    /**
     * Verifica se a data1 é maior que a data2, desconsiderando horas, minutos, segundos... 
     * @param data1
     * @param data2
     * @return
     */
    public static boolean isDataMaiorQue(Date data1, Date data2) {
    	return getData(data1).after(getData(data2));
    }
    
    /**
     * Verifica se a data é MENOR ou IGUAL a atual, desconsiderando horas, minutos, segundos... 
     * @param data
     * @return
     */
    public static boolean isDataMenorOuIgualQueAtual(Date data) {
    	Date dataAtual = new Date();
    	return isDataMenorQue(data, dataAtual) || isDatasIguais(data, dataAtual);
    }
    
    /**
     * Verifica se a data1 é MENOR ou IGUAL a data2, desconsiderando horas, minutos, segundos... 
     * @param data1
     * @param data2
     * @return
     */
    public static boolean isDataMenorOuIgualQue(Date data1, Date data2) {
    	return isDataMenorQue(data1, data2) || isDatasIguais(data1, data2);
    }
    
    /**
     * Verifica se a data1 é MENOR que a data2, desconsiderando horas, minutos, segundos... 
     * @param data1
     * @param data2
     * @return
     */
    public static boolean isDataMenorQue(Date data1, Date data2) {
    	return getData(data1).before(getData(data2));
    }
    
    /**
     * Verifica se a data está dentro do intervalor, desconsiderando horas, minutos, segundos...
     * @param data
     * @param dataInicio
     * @param dataFim
     * @return
     */
    public static boolean isDataDentroDoIntervalo(Date data, Date dataInicio, Date dataFim) {
    	return isDataMaiorOuIgualQue(data, dataInicio) && isDataMenorOuIgualQue(data, dataFim);
    }
    
    /**
     * Verifica se a data1 é maior que a data2, desconsiderando horas, minutos, segundos... 
     * @param data1
     * @param data2
     * @return
     */
    public static boolean isDatasIguais(Date data1, Date data2) {
    	return getData(data1).equals(getData(data2));
    }
    
    /**
     * Retorna a data sem horas, minutos, segundos e milesegundos
     * @param aDate
     * @return
     */
    public static Date getData(Date data) {       
        final Calendar myCalendar = Calendar.getInstance();
        myCalendar.setTime(data);
        myCalendar.set(Calendar.HOUR_OF_DAY, 0);
        myCalendar.set(Calendar.MINUTE, 0);
        myCalendar.set(Calendar.SECOND, 0);
        myCalendar.set(Calendar.MILLISECOND, 0);
        return myCalendar.getTime();
    }
    
    public static Integer getDiasUtilPeriodo(Date dataIncio, Date dataFim){    	
    	Calendar calendarioDataInicial = Calendar.getInstance();
    	Calendar calendarioDataTermino = Calendar.getInstance();
    	calendarioDataInicial.setTime(dataIncio);
    	calendarioDataTermino.setTime(dataFim);
    	int anoInicio = calendarioDataInicial.get(Calendar.YEAR);
    	int mesInicio = (calendarioDataInicial.get(Calendar.MONTH) + 1);
    	int diaInicio = calendarioDataInicial.get(Calendar.DAY_OF_MONTH);
    	
    	int anoFim = calendarioDataTermino.get(Calendar.YEAR);;
    	int mesFim = (calendarioDataTermino.get(Calendar.MONTH) + 1);;
    	int diaFim = calendarioDataTermino.get(Calendar.DAY_OF_MONTH);

    	YearMonth anoMesInicio = YearMonth.of(anoInicio, mesInicio);
    	
    	YearMonth anoMesFim = YearMonth.of(anoFim, mesFim);
    	

    	Stream<LocalDate> todosOsDiasDoMes = 
    		    Stream.iterate(anoMesInicio.atDay(diaInicio),  data -> data.plusDays(1))
    		          .limit(anoMesFim.lengthOfMonth());
    	
    	List<LocalDate> listaDosDiasUteisDoMes = new ArrayList<>();
    	
    	todosOsDiasDoMes.forEach((data) ->{
    		if(!data.getDayOfWeek().equals(DayOfWeek.SATURDAY) && !data.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
    			LocalDate dataTermino = anoMesFim.atDay(diaFim);
    			if((!data.isAfter(dataTermino))){    				
    				listaDosDiasUteisDoMes.add(data);
    			}
    		}
    	});
    	
    	return listaDosDiasUteisDoMes.size();
    }
    
    /**
     * Retorna uma string TO_DATE('valor', 'DD/MM/YYYY')
     * @param valor
     * @return
     */
    public static String getDateStrParaSql(String valor) {
		if (StringUtils.isNotEmpty(valor)) {
			return String.format(DATE_STR_PARA_SQL, valor);
		}
		
		return null;
	}
    
    /**
     * Retorna o dia da data
     * @param data
     * @return
     */
    public static Integer getDia(Date data) {       
    	final Calendar myCalendar = Calendar.getInstance();
    	myCalendar.setTime(data);
    	return myCalendar.get(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * Retorna o mês da data começando em 1
     * @param data
     * @return
     */
    public static Integer getMes(Date data) {       
    	final Calendar myCalendar = Calendar.getInstance();
    	myCalendar.setTime(data);
    	return myCalendar.get(Calendar.MONTH) + NumberUtils.INTEGER_ONE;
    }
    
    /**
     * Retorna o ano da data
     * @param data
     * @return
     */
    public static Integer getAno(Date data) {       
    	final Calendar myCalendar = Calendar.getInstance();
    	myCalendar.setTime(data);
    	return myCalendar.get(Calendar.YEAR);
    }
    
    /**
     * Verifica se a data é menor que a atual considerando horas, minutos e segundos
     */
    public static boolean isDataExpirada(Date data) {
    	return new Date().compareTo(data) == Constantes.COMPARE_TO_MAIOR;
    }
    
    /**
     * Verifica se a data é final de semana
     */
    public static boolean isFinalDeSemana(Date data) {
    	GregorianCalendar gc = new GregorianCalendar(); 
    	gc.setTime(data);
    	int diaSemana = gc.get(GregorianCalendar.DAY_OF_WEEK);
    	return diaSemana == GregorianCalendar.SATURDAY || diaSemana == GregorianCalendar.SUNDAY;
    }
    
    /**
     * Verifica se a data é sábado
     */
    public static boolean isSabado(Date data) {
    	GregorianCalendar gc = new GregorianCalendar(); 
    	gc.setTime(data);
    	int diaSemana = gc.get(GregorianCalendar.DAY_OF_WEEK);
    	return diaSemana == GregorianCalendar.SATURDAY;
    }
    
    /**
    * Verifica se a data é domingo
    */
    public static boolean isDomingo(Date data) {
    	GregorianCalendar gc = new GregorianCalendar(); 
    	gc.setTime(data);
    	int diaSemana = gc.get(GregorianCalendar.DAY_OF_WEEK);
    	return diaSemana == GregorianCalendar.SUNDAY;
    }
    
    /**
     *  Retorna o último dia do mês
     */
    public static Integer getUltimoDiaDoMes(Date data) {
    	GregorianCalendar gc = new GregorianCalendar(); 
    	gc.setTime(data);
	    return gc.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
