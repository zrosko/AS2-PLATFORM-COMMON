package hr.as2.inf.common.security;


public interface AS2SecurityConstants{
    public static String ROLE__ROLE_ID = "role_id";
    public static String ROLE__ROLE_NAME = "role_name";
    public static String ROLE__DEFAULT_FUNCTION = "default_function";
    public static String USER__USER_ID = "user_id";
    public static String USER__WORKER_ID = "worker_id";
    public static String USER__FIRST_NAME = "first_name";
    public static String USER__LAST_NAME = "last_name";
    public static String USER__USER_NAME = "user_name";
    public static String USER__PASSWORD = "password";
    public static String USER__USER_DESC = "user_desc";
    public static String FUNCTION__FUNCTION_ID = "function_id";
    public static String FUNCTION__FUNACTION_NAME = "funaction_name";
    public static String ROLE_FUNCTION__TRANSACTIONS = "transactions";
    public static String ROLE_FUNCTION__SECURITY_LEVEL = "razina";
    public static String ROLE_FUNCTION__SECURITY_LEVEL_ONE = "1";
    public static String ROLE_FUNCTION__SECURITY_LEVEL_TWO = "2";
    public static String ROLE_FUNCTION__SECURITY_LEVEL_THREE = "3";
    public static String ROLE_FUNCTION__SECURITY_LEVEL_FOUR = "4";
    //HR
    public static String USER__ID_RADNIKA = "id_radnika";
    public static String USER__ID_SLUZBE = "id_sluzbe"; //$NON-NLS-1$
    public static String USER__IME_PREZIME = "ime_prezime";
    public static String USER__NAZIV_SLUZBE = "naziv_sluzbe";
    //izbornik
    public static String ADMIN_USER = "ADMIN_USER";
    public static String ADMIN_ROLE = "ADMIN_ROLE";
    public static String ADMIN_FUNCTION = "ADMIN_FUNCTION";
    public static String ADMIN_PERSON = "ADMIN_PERSON";
    public static String ADMIN_CALENDAR = "ADMIN_CALENDAR";
    public static String ADMIN_CALENDAR_ZIMSKI = "ADMIN_CALENDAR_ZIMSKI";
    public static String ADMIN_MAINTENANCE = "ADMIN_MAINTENANCE";
    
    public static int ADMINISTRATOR_ROLE_1000 = 1000;
    public static int ADMINISTRATOR_ROLE = 1;
    public static int RUKP_ROLE = 2;
    public static int RSVP_ROLE = 3;
    public static int RSO_ROLE = 4;
    public static int OPC_ROLE = 5;
    public static int RSJH_ROLE = 6;
    public static int RSOO_ROLE = 7;
    public static int TECH_DIRECTOR_ROLE = 8;
    public static int SKLADISTAR_ROLE = 9;
    public static int RS_RECIKLAZE = 10;
    public static int ZSLUZBE_ROLE = 11;
	public static String ADMINISTRATOR_ROLE_NAME = "1"; 
	public static String RUKP_ROLE_NAME = "2";
	public static String RSVP_ROLE_NAME = "3";
	public static String RSO_ROLE_NAME = "4";
	public static String OPC_ROLE_NAME = "5";
	public static String RSJH_ROLE_NAME = "6";
	public static String RSOO_ROLE_NAME = "7";
	public static String TECH_DIRECTOR_ROLE_NAME = "8";
	public static String SKLADISTAR_ROLE_NAME = "9";
	public static String RS_RECIKLAZE_ROLE_NAME = "10";
	public static String ZSLUZBE_ROLE_NAME = "11";
	public static String DEPONANT = "12";
	public static String POMOCNIK_ODLAGANJA = "13";	
}
