/*
 * Formatiranje ulaznih polja.
 * Koristimo ga za AUIML DataFromater formatiranje polja
 * (DataFormaer.format metoh zove neku od funkcija) koja se prikazuju na 
 * formi, za Jasper reporte i formatiranje ispisa na reportu, te za formatiranje
 * polja na raznini Logickog Servera (kad klijent nije u mogucnosti formatirati polja).
 */
package hr.as2.inf.common.format;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AS2FormatterService {
    private static boolean CROATIAN = true;
    
	public static final String discardNonNumbers(String s)	{
		String ret = ""; //$NON-NLS-1$
		for (int i = 0; i < s.length(); i++)
			if ("0123456789".indexOf(s.charAt(i)) >= 0) //$NON-NLS-1$
				ret += s.charAt(i);
		return ret;
	}

	public static String formatAmount(Double amount){
		String amountFormat = "#########,00"; //$NON-NLS-1$
		DecimalFormat decf = new DecimalFormat(amountFormat); //$NON-NLS-1$
		return decf.format(amount);
	}
	public static String formatAmountForDisplay(Double amount){
		String amountFormat = "###,###,###.00"; //$NON-NLS-1$
		DecimalFormat decf = new DecimalFormat(amountFormat); //$NON-NLS-1$
		return decf.format(amount);
	}
	public static String formatAmountForDisplayBigDecimal(BigDecimal amount){
		String amountFormat = "###,###,##0.00"; //$NON-NLS-1$
		DecimalFormat decf = new DecimalFormat(amountFormat); //$NON-NLS-1$
		return decf.format(amount);
	}
   public static String formatPostotakForDisplayBigDecimal(BigDecimal amount){
        String amountFormat = "###,###,##0.0000"; //$NON-NLS-1$
        DecimalFormat decf = new DecimalFormat(amountFormat); //$NON-NLS-1$
        return decf.format(amount);
    }
   public static String formatTecajForDisplayBigDecimal(BigDecimal amount){
       String amountFormat = "##0.000000"; //$NON-NLS-1$
       DecimalFormat decf = new DecimalFormat(amountFormat); //$NON-NLS-1$
       return decf.format(amount);
   }
	public static String formatNumberForDisplayBigDecimal(BigDecimal amount){
		String amountFormat = "###,###,###,###"; //$NON-NLS-1$
		DecimalFormat decf = new DecimalFormat(amountFormat); //$NON-NLS-1$
		decf.setDecimalSeparatorAlwaysShown(true);
		return decf.format(amount);
	}

//	public static String formatAmountForDataBase(Double amount)
//	{
//		String amountFormat = "########0.00"; //$NON-NLS-1$
//		DecimalFormat decf = new DecimalFormat(amountFormat); //$NON-NLS-1$
//		return decf.format(amount);
//	}
	public static String formatDate(Date date, String dateFormat)	{
	    if(dateFormat==null){
	        if(CROATIAN) dateFormat = "dd.MM.yyyy"; //$NON-NLS-1$
	        else dateFormat = "yyyy.MM.dd"; //$NON-NLS-1$
	    }
		SimpleDateFormat shortDateFormat = new SimpleDateFormat(dateFormat);
		return shortDateFormat.format(date);
	}
	public static String formatDate(Date date){
		String dateFormat;
        if(CROATIAN) dateFormat = "dd.MM.yyyy"; //$NON-NLS-1$
        else dateFormat = "yyyy.MM.dd"; //$NON-NLS-1$

		SimpleDateFormat shortDateFormat = new SimpleDateFormat(dateFormat);
		return shortDateFormat.format(date);
	}
	/**
	 * Description: Iz datuma uzima samo vrijeme i ispisuje ga u formatu hh:mm:ss <br>
	 */ 
	public static String formatTime(Date date)	{
		String timeFormat = "K:m:s"; //$NON-NLS-1$
		SimpleDateFormat shortDateFormat = new SimpleDateFormat(timeFormat); //$NON-NLS-1$
		return shortDateFormat.format(date);
	}
	/**
	 * Iz datuma kreira string u formatu dd.mm.yyyy hh:mm:ss
	 */ 
	public static String formatDateTime(Date date)	{
		return formatDate(date) + " " + formatTime(date); //$NON-NLS-1$
	}
	public static String formatStringDateFromCalendar(Calendar cal){

	    if(cal==null){
	        cal = new GregorianCalendar();
	        cal.setTime(new java.util.Date());
	    }
	    String dateFormat;
	    if(CROATIAN) dateFormat = "dd.MM.yyyy"; //$NON-NLS-1$
        else dateFormat = "yyyy.MM.dd"; //$NON-NLS-1$
		SimpleDateFormat shortDateFormat = new SimpleDateFormat(dateFormat);
		return shortDateFormat.format(cal.getTime());
	}
	public static String formatStringTimeFromCalendar(Calendar cal){
	    //return format 11:59:59 PM needed by AUIML time fromater
	    if(cal==null){
	        cal = new GregorianCalendar();
	        cal.setTime(new java.util.Date());
	    }
        String dateFormat = "kk:mm:ss"; //$NON-NLS-1$
		SimpleDateFormat shortDateFormat = new SimpleDateFormat(dateFormat);
		String time_ = shortDateFormat.format(cal.getTime());
		if(time_.equals("24:00:00"))
		    time_="23:59:59";
		return time_;
	}
}
