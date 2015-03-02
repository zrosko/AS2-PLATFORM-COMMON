package hr.as2.inf.common.core;

import hr.as2.inf.common.logging.AS2Trace;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * AS2Context is used by client and by server to hold application
 * common settings.
 * Some of the context values are read from 
 * AS2Context.properties file.
 */
public final class AS2Context implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private static AS2Context _instance = null;
	private static boolean initDone = false;
  	private static boolean destroyDone = false;
	private static String propertiesPath = "hr/adriacomsoftware/resources";
	public static final String TITLES_PATH = "hr/adriacomsoftware/resources/common/AS2Titles";
	public static final String REPORTS_PATH = "hr/adriacomsoftware/resources/common/reports/"; 
	public static final String ICONS_PATH = "hr/adriacomsoftware/resources/client/images/";
	public static final String J2EE_ICONS_PATH = "hr/adriacomsoftware/resources/client/images/";
	/*
	 * Rules repository path.
	 */
	public static String RULESET_DIR="C:/QuickRules1_3sp1/qrules/projects/qdemo/data/";
	/*
	 * Class name.
	 */
	public String _class;
	/*
	 * Property file values.
	 */
	public Properties _properties;
	/*
	 * Session type defaults to NO session.
	 */
	public int SESSION_TYPE = 0;
	/*
	 * HTTP request time out.
	 */
	public long TIMEOUT = 0;
	/*
	 * Desktop refresh time out in millisec.
	 */
	public long DESKTOP_REFRESH_TIMEOUT = 120000;//2 minuta
	/*
	 * HTTP request retry count.
	 */
	public int HTTP_RETRY_NO = 1;
	/*
	 * HTTP retry sleep in milliseconds.
	 */
	public long HTTP_RETRY_SLEEP = 0;
	/*
	 * Server Transaction time out.
	 */
	public long TXNTIMEOUT = 0;
	public int JTA_TXNTIMEOUT = 5;
	/*
	 * Transport client/server subprotocol (e.g. HTTP, HTTPS).
	 */
	public static String TRANSPORT_PROTOCOL = "HTTP";
	/*
	 * HTTP transport servlet name.
	 */
	public static String TRANSPORT_SERVLET_NAME = "hr.as2.inf.server.requesthandlers.http.AS2ServerRequestHandlerHttp";
	/*
	 * Query results caching indicator.
	 */
	public boolean SERVER_CACHING_IND = false;
	/* Business delegate design pattern cache indicator. */
	public boolean CLIENT_CACHING_IND = true;
	public boolean MANDATORY_CHECKING_IND = true;
  	/*
   	* Time between starting cleaner thread in miliseconds
   	*/
  	public long CACHE_CLEANER_REPEAT_TIME = 60000;
    /*
     * time to keep data from XYZ in cache in minutes
     */
    public int XYZ_CACHE_TIME = 240;
    /*
     * time to keep data from ABC in cache in minutes
     */
    public int ABC_CACHE_TIME = 240;
	/*
	 * Query results caching maximum.
	 */
	public int MAX_CACH_SIZE = 0;
	/*
	 * Trace indicator.
	 */
	public String TRACE_ON = "YES";
	/*
	 * Trace level.
	 */
	public String TRACE_LEVEL = "W";
	/*
	 * Transport client/server protocol (e.g. HTTP, LOCAL, RMI, IIOP, JMS).
	 */
	public static String TRANSPORT = "LOCAL";
	public static String GUEST_USER = "N";
	/*
	 * Server host id.
	 */
	public String HOST = "127.0.0.1";
	/*
	 * ApplicationController name.
	 */
	public static String APPLICATION_CONTROLLER_NAME = "hr.as2.inf.server.invokers.AS2InvokerDefault";
	/*
	 * EJBApplicationController name.
	 */
	public String EJB_APPLICATION_CONTROLLER_JNDI_NAME = "EJBApplicationControllerServer";
	/*
	 * EJBApplicationController url.
	 */
	public String EJB_APPLICATION_CONTROLLER_PROVIDER_URL = "iiop://127.0.0.1:900";
	//JMS MDB future parametar.
	/*
	 * JNDI Security provider url.
	 */
	public String JNDI_SECURITY_PROVIDER_URL = "ldap://adriacom_server";
	public String HTTP_HOME = "http://www.google.hr";
	public String HTTP_HELP = "http://www.google.hr";
	public String JASPER_TITLE = "Čistoća d.o.o. Zadar - Tehnički sektor izvještaji";
	
	/*
	 * External transaction context indicator. If J2EETransaction is used to commit
	 * or rollback this is set to "false" otherwise to "true".
	 */
	public boolean EXTERNAL_TXN_COMMIT_IND = false;
	/*
	 * Rule Validation indikator. Rules can be validater on copule of levels.
	 * For now we support one level only.
	 * 
	 */
	public boolean SCREEN_RULE_VALIDATION_IND = false;
	/*
     * Jasper report viewer dimensions.
     */
    public int REPORT_VIEWER_X = 700;
    public int REPORT_VIEWER_Y = 450;
    public int ALARM_BROJ_DANA = 7;
    public int ALARM_BROJ_DANA_MALI = 1;
    public int ALARM_BROJ_KILOMETARA = 500;
    public int ALARM_BROJ_SATI = 30;
    public int VALUE_LIST_HANDLER_ROW_SIZE = 1000;
    public String DEFAULT_DEPONIJ_ID = "1";
	/*
	 * Singleton objects reference holder. There are singleton objects being
	 * garbage collected (not supposed to). Since it does cause exceptions, all singleton references
	 * should be stored in this Vector to keep the reference to them and
	 * to prevent the garbage collection.
	 */
	private static Vector<Object> _singletonReferences = new Vector<Object>();
	
private AS2Context() {
	_class = getClass().getName();
	_singletonReferences = new Vector<Object>();
	String helperString; 
	propertiesPath="hr/adriacomsoftware/resources";
	//PropertyConfigurator.configure(J2EEHelper.readPropertyFileAsURL(propertiesPath + "log4j.properties")); 
 	//J2EETrace.setUseLog4j(true);//if set up	 
	_properties = AS2Helper.readPropertyFileAsURL(propertiesPath + "/common/J2EEContext.properties");
	SESSION_TYPE = AS2Helper.getIntProperty(_properties, _class + ".SESSION_TYPE", 0);
	//? for HTTP or ?
	TIMEOUT = AS2Helper.getIntProperty(_properties, _class + ".TIMEOUT", 0);	
	DESKTOP_REFRESH_TIMEOUT = AS2Helper.getIntProperty(_properties, _class + ".DESKTOP_REFRESH_TIMEOUT", 120000);
	HTTP_RETRY_NO = AS2Helper.getIntProperty(_properties, _class + ".HTTP_RETRY_NO", 1);
	HTTP_RETRY_SLEEP = AS2Helper.getIntProperty(_properties, _class + ".HTTP_RETRY_SLEEP", 0);
	TXNTIMEOUT = AS2Helper.getIntProperty(_properties, _class + ".TXNTIMEOUT", 0);
	JTA_TXNTIMEOUT = AS2Helper.getIntProperty(_properties, _class + ".JTA_TXNTIMEOUT", 0);
	//helperString = _properties.getProperty(_class + ".BDL_JTA_IND", "FALSE");
	//JTA_IND = new Boolean(helperString).booleanValue();
	helperString = _properties.getProperty(_class + ".SERVER_CACHING_IND", "false");
	SERVER_CACHING_IND = new Boolean(helperString).booleanValue();
	helperString = _properties.getProperty("CLIENT_CACHING_IND", "true");
	CLIENT_CACHING_IND = new Boolean(helperString).booleanValue();
	helperString = _properties.getProperty("MANDATORY_CHECKING_IND", "true");
	MANDATORY_CHECKING_IND = new Boolean(helperString).booleanValue();
	CACHE_CLEANER_REPEAT_TIME = AS2Helper.getLongProperty(_properties, _class + ".CACHE_CLEANER_REPEAT_TIME", 60000);
  	XYZ_CACHE_TIME = AS2Helper.getIntProperty(_properties, _class + ".XYZ_CACHE_TIME", 240);
  	ABC_CACHE_TIME = AS2Helper.getIntProperty(_properties, _class + ".ABC_CACHE_TIME", 240);
	MAX_CACH_SIZE = AS2Helper.getIntProperty(_properties, _class + ".MAX_CACH_SIZE", 0);
	ALARM_BROJ_DANA = AS2Helper.getIntProperty(_properties, _class + ".ALARM_BROJ_DANA", 7);
	ALARM_BROJ_DANA_MALI = AS2Helper.getIntProperty(_properties, _class + ".ALARM_BROJ_DANA_MALI", 1);
	ALARM_BROJ_KILOMETARA = AS2Helper.getIntProperty(_properties, _class + ".ALARM_BROJ_KILOMETARA", 500);
	ALARM_BROJ_SATI = AS2Helper.getIntProperty(_properties, _class + ".ALARM_BROJ_SATI", 30);
	VALUE_LIST_HANDLER_ROW_SIZE = AS2Helper.getIntProperty(_properties, _class + ".VALUE_LIST_HANDLER_ROW_SIZE", 10);
	TRACE_ON = _properties.getProperty(_class + ".TRACE_ON", "YES");
	TRACE_LEVEL = _properties.getProperty(_class + ".TRACE_LEVEL", "W");
	APPLICATION_CONTROLLER_NAME = _properties.getProperty(_class + ".APPLICATION_CONTROLLER_NAME", "hr.adriacomsoftware.inf.services.J2EEDefaultApplicationController");
	//if(!APPLICATION_CONTROLLER_NAME.equals("com.adriacomsoftware.inf.services.J2EEDefaultApplicationController"))
		//EXTERNAL_TXN_COMMIT_IND = true;
	EJB_APPLICATION_CONTROLLER_JNDI_NAME= _properties.getProperty(_class + ".EJB_APPLICATION_CONTROLLER_JNDI_NAME", "EJBApplicationControllerServer");
	EJB_APPLICATION_CONTROLLER_PROVIDER_URL= _properties.getProperty(_class + ".EJB_APPLICATION_CONTROLLER_PROVIDER_URL", "iiop://127.0.0.1:900");
	JNDI_SECURITY_PROVIDER_URL= _properties.getProperty(_class + ".JNDI_SECURITY_PROVIDER_URL", "ldap://banksoft_server");
	RULESET_DIR = _properties.getProperty(_class + ".RULESET_DIR", "C:/QuickRules1_3sp1/qrules/projects/qdemo/data/");
	helperString = _properties.getProperty(_class + ".SCREEN_RULE_VALIDATION_IND", "FALSE");
	SCREEN_RULE_VALIDATION_IND = new Boolean(helperString).booleanValue();
	REPORT_VIEWER_X = AS2Helper.getIntProperty(_properties, _class + ".REPORT_VIEWER_X", 700);
	REPORT_VIEWER_Y = AS2Helper.getIntProperty(_properties, _class + ".REPORT_VIEWER_Y", 450);
	HTTP_HOME = _properties.getProperty("HTTP_HOME", "http://www.google.hr");
	JASPER_TITLE = _properties.getProperty("JASPER_TITLE","Adriacom Software d.o.o.");
	DEFAULT_DEPONIJ_ID = _properties.getProperty("DEFAULT_DEPONIJ_ID", "1");
		
	if(TRACE_ON.equals(AS2Constants.YES))
		AS2Trace.turnTracingOn();
	else
		AS2Trace.turnTracingOff();
	
	if(TRACE_LEVEL.equals("N"))
		AS2Trace.setTraceLevel(AS2Trace.N);
	else if(TRACE_LEVEL.equals("E"))
		AS2Trace.setTraceLevel(AS2Trace.E);
	else if(TRACE_LEVEL.equals("W"))
		AS2Trace.setTraceLevel(AS2Trace.W);
	else if(TRACE_LEVEL.equals("I"))
		AS2Trace.setTraceLevel(AS2Trace.I);
	else if(TRACE_LEVEL.equals("A"))
		AS2Trace.setTraceLevel(AS2Trace.A);
}
public void destroy() {
    if (!destroyDone) {
        Class<?> c = null;
        Method getInstance = null;
        Method m = null;
        Class<?> parameters[] = new Class[0];
        Class<?> getInstanceParameters[] = new Class[0];

        Object target = null;
        Object[] arguments = new Object[0];
        Object[] getInstanceArguments = new Object[0];

        AS2Trace.trace(AS2Trace.I, "AS2Context.Destroy begin ");

        Properties p =
            AS2Helper.readPropertyFileAsURL(propertiesPath + "/common/AS2ContextDestroy.properties");

        Enumeration<?> E = p.propertyNames();

        while (E.hasMoreElements()) {
            String classToCall = (String) E.nextElement();
            String methodToCall = (String) p.getProperty(classToCall);
            AS2Trace.trace(
                AS2Trace.I,
                "AS2ContextDestroy Calling: " + classToCall + "." + methodToCall);
            try {

                c = Class.forName(classToCall);

                getInstance = c.getMethod("getInstance", getInstanceParameters);
                m = c.getMethod(methodToCall, parameters);

                /********************************************************************/
                target = getInstance.invoke(target, getInstanceArguments);
                m.invoke(target, arguments);
                /********************************************************************/

            } catch (Exception e) {
                AS2Trace.trace(AS2Trace.W, e, "AS2Context.Destroy Problem");
            }
        } //while

        AS2Trace.trace(AS2Trace.I, "AS2Context.Destroy end ");
        destroyDone = true;
    }
}
public static synchronized AS2Context getInstance () {
	if (_instance==null)
		_instance=new AS2Context();
	return _instance;
}
public Properties getProperties() {
	return _properties;
}
public String getProperty(String name, String defVal) {
	return _properties.getProperty(name,defVal);
}
public String getProperty(String name) {
	return _properties.getProperty(name);
}
public static String getPropertiesPath() {
	return propertiesPath;
}
public void init() {
    if (!initDone) {
        Class<?> c = null;
        Method getInstance = null;
        Method m = null;
        Class<?> parameters[] = new Class[0];
        Class<?> getInstanceParameters[] = new Class[0];

        Object target = null;
        Object[] arguments = new Object[0];
        Object[] getInstanceArguments = new Object[0];

        AS2Trace.trace(AS2Trace.I, "AS2Context.Init begin ");

        Properties p =
            AS2Helper.readPropertyFileAsURL(propertiesPath + "/common/AS2ContextInit.properties");

        Enumeration<?> E = p.propertyNames();

        while (E.hasMoreElements()) {
            String classToCall = (String) E.nextElement();
            String methodToCall = (String) p.getProperty(classToCall);
            AS2Trace.trace(
                AS2Trace.I,
                "AS2ContextInit Calling: " + classToCall + "." + methodToCall);
            try {

                c = Class.forName(classToCall);

                getInstance = c.getMethod("getInstance", getInstanceParameters);
                m = c.getMethod(methodToCall, parameters);

                /********************************************************************/
                target = getInstance.invoke(target, getInstanceArguments);
                m.invoke(target, arguments);
                /********************************************************************/

            } catch (Exception e) {
                AS2Trace.trace(AS2Trace.W, e, "AS2Context.Init Problem");
            }
        } //while

        AS2Trace.trace(AS2Trace.I, "AS2Context.Init end ");
        initDone = true;
    }
}
public static void setPropertiesPath(String value) {
	propertiesPath=value;
}
public static void setSingletonReference(Object value) {
	
	_singletonReferences.addElement(value);	
}
public int getIntProperty(String prop_name){
    return AS2Helper.getIntProperty(_properties, prop_name, 0);
}
public void setLoggingLevel(Level value){
	Logger.getLogger("AS2Logging").setLevel(value);
	
}
public String toString() {
	return (
		"\n SESSION_TYPE:			" + SESSION_TYPE +
		"\n TIMEOUT:				" + TIMEOUT +
		"\n HTTP_RETRY_NO:			" + HTTP_RETRY_NO +
		"\n HTTP_RETRY_SLEEP:		" + HTTP_RETRY_SLEEP +
		"\n TXNTIMEOUT:				" + TXNTIMEOUT +
		"\n TRANSPORT_PROTOCOL:		" + TRANSPORT_PROTOCOL +
		"\n TRANSPORT_SERVLET_NAME:	" + TRANSPORT_SERVLET_NAME +
		"\n SERVER_CACHING_IND:		" + SERVER_CACHING_IND +
		"\n CLIENT_CACHING_IND:		" + CLIENT_CACHING_IND +
		"\n MAX_CACH_SIZE	:		" + MAX_CACH_SIZE +
		"\n TRACE_ON:				" + TRACE_ON +
		"\n TRACE_LEVEL:			" + TRACE_LEVEL +
		"\n TRANSPORT:				" + TRANSPORT +
		"\n");
}
}
