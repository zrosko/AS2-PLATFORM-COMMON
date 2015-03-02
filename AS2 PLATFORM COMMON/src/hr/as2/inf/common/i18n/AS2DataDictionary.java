package hr.as2.inf.common.i18n;

import hr.as2.inf.common.core.AS2Context;
import hr.as2.inf.common.core.AS2Helper;

import java.util.Properties;

/*
 * AS2DataDictionary is used by client and server to hold the application
 * data dictionary. Data dictionary source is the data base model. Fields are 
 * generated using data base model meta data. 
 * AS2DataDictionary.properties file is used in case there is a 
 * data base change before the J2EEDataDictionary is refreshed and compiled.
 */
public final class AS2DataDictionary {
	/*
	 * Singleton reference.
	 */
	private static AS2DataDictionary _instance = null;
	/*
	 * Property file values.
	 */
	public Properties _properties;
	/* polja parametri za izjvestaje */
	public static String OD_DATUMA = "od_datuma";
	public static String DO_DATUMA = "do_datuma";
	/* polja parametri za izjvestaje */
	public static String VALID_IND_YES = "Y";
	public static String VALID_IND_NO = "N";
	/************* Zajednicki atrubuti *******************/
	public static String DATUM = "datum";
	public static String ZADANI_DATUM = "zadani_datum";
	public static String DATUM_OD = "datum_od";
	public static String DATUM_DO = "datum_do";
	public static String USER_LAST_NAME = "last_name";
	public static String USER_FIRST_NAME = "first_name";
	public static String PRVI_UNOS_USER_ID = "prvi_unos_user_id";
	public static String PRVI_UNOS_VRIJEME = "prvi_unos_vrijeme";
	public static String ZADNJA_PROMJENA_USER_ID = "zadnja_promjena_user_id";
	public static String ZADNJA_PROMJENA_VRIJEME = "zadnja_promjena_vrijeme";
	public static String VALID_IND = "valid_ind";

	private AS2DataDictionary() {
		// ovdje upotrebiti reflection za citanje i azuriranje imena kolana iz
		// prop datoteke
		_properties = AS2Helper.readPropertyFileAsURL(AS2Context
				.getPropertiesPath() + "/common/AS2DataDictionary.properties");
		// TS_VOZILO__REGISTARSKA_OZNAKA=
		// _properties.getProperty("TS_VOZILO__REGISTARSKA_OZNAKA",
		// "registarska_oznaka");
		// TS_POVJEST_VOZILA__KILOMETRAZA=
		// _properties.getProperty("TS_POVJEST_VOZILA__KILOMETRAZA",
		// "kilometraza");
	}

	public static synchronized AS2DataDictionary getInstance() {
		if (_instance == null)
			_instance = new AS2DataDictionary();
		return _instance;
	}

	public Properties getProperties() {
		return _properties;
	}

	public String getProperty(String name, String defaultValue) {
		return _properties.getProperty(name, defaultValue);
	}

	public String getProperty(String name) {
		return _properties.getProperty(name);
	}
}
