package hr.as2.inf.common.core;

public interface AS2Constants {
		public final static String MODAL_MENU_DOUBLE_CLICK ="MENU_double_click";
		public final static String PROPERTIES_PATH = "/as2/settings/"; 
		public final static String RELATIVE_IMAGE_LOCATION = "/as2/images/"; 
		public final static String YES = "YES"; 
		public final static String NO = "NO"; 
		public final static String JDBC = "JDBC"; 
		public final static String MQ = "MQ"; 
		public final static String AS400 = "AS400"; 
		public final static String USER_OBJ = "@@USER_OBJ"; 
		public final static String BLANK = " ";
		public static final String FIND_CRITERIA = "FIND_CRITERIA"; 
		public static final String ORDER_BY_CLAUSE = "ORDER_BY_CLAUSE"; 
		public static final String SELECT_CLAUSE = "SELECT_CLAUSE"; 
		public static final String JOB_USER = "job_user"; 
		public static final String JOB_NUMBER = "job_number"; 
		public static final String JOB_NAME = "job_name"; 
		public static final String COM_OK_CONT = "00CCCC"; 
		public static final String COM_OK_END = "000000"; 
		public final static String SHOW_RESULT = "@@SHOW_RESULT";
		public final static String PARENT_MODEL = "@@parent_model";
		public final static String TS_KALENDAR = "@@ts_kalendar";
		//pomocna konstanta
		public final static String SELECT = "SELECT_"; 
		public final static String LISTA_ALARMA_KM = "@@ALARMI_KM"; 
		public final static String LISTA_ALARMA_DANI = "@@ALARMI_DANI"; 
		//kad klijent treba prikazati rezultat u Info prozoru (Message Box sa 
		//porukom napr. ZA-2006-09-12)
		//na serveru ili drugdje se postavi polje u vo sa imenom @@SHOW_RESULT
		//potrebno je view contreolleru postaviti indikator _show result
		//public final static String SHOW_RESULT = "@@SHOW_RESULT"; //$NON-NLS-1$
	    //SQL datepart dani nisu isti kao i java.Date dani. Stoga u JDBC
	    //programima oduzimamo jedan dan.
	    /*The value (<tt>0</tt> = Sunday, <tt>1</tt> = Monday, 
	    * <tt>2</tt> = Tuesday, <tt>3</tt> = Wednesday, <tt>4</tt> = 
	    * Thursday, <tt>5</tt> = Friday, <tt>6</tt> = Saturday) */
	    public final static int NEDJELJA = 1;
	    public final static int PONEDJELJAK = 2;
	    public final static int UTORAK = 3;
	    public final static int SRIJEDA = 4;
	    public final static int CETVRTAK = 5;
	    public final static int PETAK = 6;
	    public final static int SUBOTA = 7;  
	    //koristimo 9 za neodredene dane
		public static String NED = "ned";
		public static String PON = "pon";
		public static String UTO = "uto";
		public static String SRI = "sri";
		public static String CET = "cet";
		public static String PET = "pet";
		public static String  SUB = "sub";
		public final static String AKCIJA = "@akcija";
		public final static String IZRACUNAJ = "Izracunaj";
		public final static String ORDER_BY = "@@order_by";
		//------------------------------- NEW
		public static String J2EE_VALID_TRUE = "1";
		public static String J2EE_VALID_FALSE = "0";
		public static String ADMIN_STATISTICS = "ADMIN_STATISTICS";
		public static String ADMIN_EXCEPTIONS = "ADMIN_EXCEPTIONS";
		public static String ADMIN_CONFIGURATION = "ADMIN_CONFIGURATION";
		public static String ADMIN_JDBC = "ADMIN_JDBC";
		public static String EXCEL = "EXCEL";
		public static final int ERROR = 1;
	    public static final int INFORMATION = 2;
	    public static final int WARNING = 3;
	    public static final int QUESTION = 4;
	    public static final int PLAIN = 5;
}
