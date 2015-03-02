package hr.as2.inf.common.data.as400;

import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.types.AS2Date;

import java.util.Calendar;

/**
 * As400.
 */
public class AS2AS400SpoolFileVo extends AS2Record {
	private static final long serialVersionUID = 1L;
	public final static String TEST = "T";
    public final static String DATUM_OD = "datum_od";
    public final static String DATUM_DO = "datum_do";
    public final static String KORISNIK = "korisnik";
    public final static String SPOOL_FILE_NAZIV = "spool_file_naziv";
    public final static String AS400_JOB_SPFILE__SPFILE_TEKST = "spfile_tekst";
        
    /* Attributes names /> */
    /* </ Constructors */
    public AS2AS400SpoolFileVo() {
        super();
    }

    public AS2AS400SpoolFileVo(AS2Record value) {
        super(value);
    }

    /* Constructors /> */
    public static String getDateFormatNormalToCYYMMDD(Calendar date) {
        String date_ = AS2Date.convertCalendarTimeToStringDate(date);
        String century = date_.substring(0,2);
        String year = date_.substring(2,4);
        String month = date_.substring(5,7);
        String day = date_.substring(8,10);
        if(century.matches("20"))
            century = "1";
        else
            century = "0";
        System.out.println(century+year+month+day);
        return (century+year+month+day);
    }
    public static String getDateFormatCYYMMDDToNormal(String date_) {
        //String date_ = J2EEDate.convertCalendarTimeToStringDate(date);
        String century = date_.substring(0,1);
        String year = date_.substring(1,3);
        String month = date_.substring(3,5);
        String day = date_.substring(5,7);
        if(century.matches("1"))
            century = "20";
        else
            century = "19";
        return (day+"."+month+"."+century+year+".");
    }
    public static String getTimeFormatNormal(String time_) {
        String hours = time_.substring(0,2);
        String minutes = time_.substring(2,4);
        String seconds = time_.substring(4,6);
        return (hours+":"+minutes+":"+seconds);
    }
/* < Getters & Setters */
    
    
    public Calendar getDatumOd() {
        return getAsCalendar(DATUM_OD);
    }
    public Calendar getDatumDo() {
        return getAsCalendar(DATUM_DO);
    }
    public String getKorisnik() {
        return getAsStringOrEmpty(KORISNIK);
    }
    public String getSpoolFileNaziv() {
        return getAsStringOrEmpty(SPOOL_FILE_NAZIV);
    }
    public String getSpfileTekst() {
        return getAsStringOrEmpty(AS400_JOB_SPFILE__SPFILE_TEKST);
    }
    //***GET
    public void setDatumOd(Calendar value) {
        setCalendarAsDateString(value, DATUM_OD);
    }
    public void setDatumDo(Calendar value) {
        setCalendarAsDateString(value, DATUM_DO);
    }
    public void setKorisnik(String value) {
        set(KORISNIK, value);
    }
    public void setSpoolFileNaziv(String value) {
        set(SPOOL_FILE_NAZIV, value);
    }
    public void setSpfileTekst(String value) {
        set(AS400_JOB_SPFILE__SPFILE_TEKST, value.trim());
    }
}