package hr.as2.inf.common.admin.dto;

import hr.as2.inf.common.data.AS2Record;

public class AS2ExceptionVo extends AS2Record { 
	private static final long serialVersionUID = 1L;
	public static String EXCEPTION__ID = "Id";
    public static String EXCEPTION__USER = "User";
    public static String EXCEPTION__SERVICE = "Service";
    public static String EXCEPTION__OCCURED_DATE = "OccuredDate";
    public static String EXCEPTION__ERROR_CODE = "ErrorCode";
    public static String EXCEPTION__ERROR_DESCRIPTION = "ErrorDescription"; 
    public static String EXCEPTION__TECHNICAL_ERROR_DESCRIPTION = "TechnicalErrorDescription";    
    public static String EXCEPTION__CAUSE_EXCEPTIONS = "CauseExceptions";
    public static String EXCEPTION__STACK_TRACE = "StackTrace"; 
    
    public AS2ExceptionVo()	{
		super();
	}
	public AS2ExceptionVo(AS2Record value)	{
		super(value);
	}
	public String getId() {
		return getAsString(EXCEPTION__ID);
	}
	public String getUser() {
		return getAsString(EXCEPTION__USER);
	}
	public String getService() {
		return getAsString(EXCEPTION__SERVICE);
	}
	public String getOccuredDate() {
		return getAsString(EXCEPTION__OCCURED_DATE);
	}
	public String getErrorCode() {
		return getAsString(EXCEPTION__ERROR_CODE);
	}
	public String getErrorDescription() {
		return getAsString(EXCEPTION__ERROR_DESCRIPTION);
	}	
	public String getTechnicalErrorDescription() {
		return getAsString(EXCEPTION__TECHNICAL_ERROR_DESCRIPTION);
	}
	public String getCauseExceptions() {
		return getAsString(EXCEPTION__CAUSE_EXCEPTIONS);
	}
	public String getStackTrace() {
		return getAsString(EXCEPTION__STACK_TRACE);
	}
	//setters
	public void setId(String value) {
		set(EXCEPTION__ID, value);
	}
	public void setUser(String value) {
		set(EXCEPTION__USER, value);
	}
	public void setService(String value) {
		set(EXCEPTION__SERVICE, value);
	}
	public void setOccuredDate(String value) {
		set(EXCEPTION__OCCURED_DATE, value);
	}
	public void setErrorCode(String value) {
		set(EXCEPTION__ERROR_CODE, value);
	}
	public void setErrorDescription(String value) {
		set(EXCEPTION__ERROR_DESCRIPTION, value);
	}	
	public void setTechnicalErrorDescription(String value) {
		set(EXCEPTION__TECHNICAL_ERROR_DESCRIPTION, value);
	}
	public void setCauseExceptions(String value) {
		set(EXCEPTION__CAUSE_EXCEPTIONS, value);
	}
	public void setStackTrace(String value) {
		set(EXCEPTION__STACK_TRACE, value);
	}
}
