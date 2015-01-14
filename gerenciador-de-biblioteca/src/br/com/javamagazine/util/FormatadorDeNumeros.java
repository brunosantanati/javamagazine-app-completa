package br.com.javamagazine.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class FormatadorDeNumeros {
	
	public static String formatarBigDecimalComoMoeda(BigDecimal numero){
		Locale localeBrasil = new Locale("pt","BR");
		DecimalFormatSymbols moedaReal = new DecimalFormatSymbols(localeBrasil);
		DecimalFormat df = new DecimalFormat("¤ ###,###,##0.00", moedaReal);
		return df.format(numero);
	}
	
}
