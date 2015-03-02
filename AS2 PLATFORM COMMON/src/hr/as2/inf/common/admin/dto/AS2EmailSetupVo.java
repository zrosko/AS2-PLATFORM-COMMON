package hr.as2.inf.common.admin.dto;

import hr.as2.inf.common.data.AS2Record;

public class AS2EmailSetupVo extends AS2Record {
	private static final long serialVersionUID = 1L;
	public static String EMAIL_SETUP__MAILER_SMTP_SERVER = "MAILER_SMTP_SERVER";
    public static String EMAIL_SETUP__SETTINGS_PATH = "SETTINGS_PATH";
    public static String EMAIL_SETUP__MAILER_TO = "MAILER_TO"; 
    public static String EMAIL_SETUP__MAILER_MAIL_FROM = "MAILER_MAIL_FROM";
    public static String EMAIL_SETUP__ENVIRONMENT_NAME = "ENVIRONMENT_NAME";
    public static String EMAIL_SETUP__DTD_URL = "DTD_URL";
    public static String EMAIL_SETUP__XSL_XMLINSTANCE_FILE = "XSL_XMLINSTANCE_FILE"; 
    public static String EMAIL_SETUP__XSD_FILE = "XSD_FILE";    
    public static String EMAIL_SETUP__MAILER_ENABLED = "MAILER_ENABLED";
    public static String EMAIL_SETUP__MAILER_BCC = "MAILER_BCC";    
    public static String EMAIL_SETUP__MAILER_CC = "MAILER_CC"; 
         
    public AS2EmailSetupVo()	{
		super();
	}
	public AS2EmailSetupVo(AS2Record value)	{
		super(value);
	}
	public String getMailerSmtpServer() {
		return getAsString(EMAIL_SETUP__MAILER_SMTP_SERVER);
	}
	public String getSettingsPath() {
		return getAsString(EMAIL_SETUP__SETTINGS_PATH);
	}
	public String getMailerTo() {
		return getAsString(EMAIL_SETUP__MAILER_TO);
	}
	public String getMailerMailFrom() {
		return getAsString(EMAIL_SETUP__MAILER_MAIL_FROM);
	}
	public String getEnvironmentName() {
		return getAsString(EMAIL_SETUP__ENVIRONMENT_NAME);
	}
	public String getDtdUrl() {
		return getAsString(EMAIL_SETUP__DTD_URL);
	}	
	public String getXslXmlinstanceFile() {
		return getAsString(EMAIL_SETUP__XSL_XMLINSTANCE_FILE);
	}
	public String getXsdFile() {
		return getAsString(EMAIL_SETUP__XSD_FILE);
	}
	public String getMailerEnabled() {
		return getAsString(EMAIL_SETUP__MAILER_ENABLED);
	}	
	public String getMailerBcc() {
		return getAsString(EMAIL_SETUP__MAILER_BCC);
	}
	public String getMailerCc() {
		return getAsString(EMAIL_SETUP__MAILER_CC);
	}
	//setters
	public void setMailerSmtpServer(String value) {
		set(EMAIL_SETUP__MAILER_SMTP_SERVER,value);
	}
	public void setSettingsPath(String value) {
		set(EMAIL_SETUP__SETTINGS_PATH,value);
	}
	public void setMailerTo(String value) {
		set(EMAIL_SETUP__MAILER_TO,value);
	}
	public void setMailerMailFrom(String value) {
		set(EMAIL_SETUP__MAILER_MAIL_FROM,value);
	}
	public void setEnvironmentName(String value) {
		set(EMAIL_SETUP__ENVIRONMENT_NAME,value);
	}
	public void setDtdUrl(String value) {
		set(EMAIL_SETUP__DTD_URL,value);
	}	
	public void setXslXmlinstanceFile(String value) {
		set(EMAIL_SETUP__XSL_XMLINSTANCE_FILE,value);
	}
	public void setXsdFile(String value) {
		set(EMAIL_SETUP__XSD_FILE,value);
	}
	public void setMailerEnabled(String value) {
		set(EMAIL_SETUP__MAILER_ENABLED,value);
	}	
	public void setMailerBcc(String value) {
		set(EMAIL_SETUP__MAILER_BCC,value);
	}
	public void setMailerCc(String value) {
		set(EMAIL_SETUP__MAILER_CC,value);
	}
}
