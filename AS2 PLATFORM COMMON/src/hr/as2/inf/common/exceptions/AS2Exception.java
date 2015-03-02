/*
 * ====================================================================
 * RABAF - Reference Architecture for Business Applications Framework
 *
 * Copyright (C) 2014 Adriacom Software Inc.
 * zrosko@gmail.com
 *     
 * ====================================================================
 */
package hr.as2.inf.common.exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.Vector;
/**
 * The infrastructure common exception class.
 * The concept of handling the exceptions is to catch
 * the exception in the current layer (bl, da, facade interface,
 * infrastructure) and then rethrow current layer exception
 * with the lower layer exception embeded in the current one
 * by using addCausedException.
 * 
 * @version 1.0 
 * @date 	Dec 4, 1999
 * @author 	Zdravko Rosko
 * @update  Feb 27, 2004. Changed to inherit from RuntimeException
 *			instead from Exception. Started using "non checked"
 *			exception aproach.
 */
public class AS2Exception extends RuntimeException implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String _stackTrace = "";
    private String _errorCode = "";
    private String _errorDescription = null;
    private String _resourceBundle =
        "hr.as2.inf.common.exceptions.AS2DefaultResourceBundle";
    private String _technicalErrorDescription = " ";
    private int _severity = 1;
    private String _recoveryAction = " ";
    private Date _occuredDate = new Date();

    // A description of the object that causes this exception.
    private String _sourceObjectDescription = " ";
    // A list of all other exceptions that cause this exception.
    private Vector<Throwable> _causeExceptions;
    public Exception _originalException;
    private java.lang.Object _errorDetails;
    private String _field_one="";
    private String _field_two="";
    private boolean _readyForClient = false;
public AS2Exception(){	
	super("J2EEException has occured.");	
}
public AS2Exception(Exception e) {
    super(e.toString());
    _originalException = e;
    StringWriter sw = new StringWriter();
    e.printStackTrace(new PrintWriter(sw));
    _stackTrace = sw.toString();
}
public AS2Exception (String errorCode){
	this(errorCode, null, null, 0, null, null);
}
public AS2Exception(
    String errorCode, 
    String resourceBundle, 
    String technicalErrorDescription, 
    int severity, 
    String recoveryAction, 
    Date occuredDate) {
    super(errorCode);
    setResourceBundle(resourceBundle);
    setErrorCode(errorCode);
    setTechnicalErrorDescription(technicalErrorDescription);
    setSeverity(severity);
    setRecoveryAction(recoveryAction);
    setOccuredDate(occuredDate);
}
public void setFieldOne(String value){
    _field_one = value;
}
public void setFieldTwo(String value){
    _field_two = value;
}
public boolean isReadyForClient(){
    return _readyForClient;
}
public void setReadyForClient(boolean value){
    _readyForClient=value;
}
public void addCauseException(Throwable e) {
	StringWriter sw = new StringWriter();
	e.printStackTrace(new PrintWriter(sw));
	_stackTrace = sw.toString();
	//?? nema printstacktrace za vise nested iznimki
    if (_causeExceptions == null)
        _causeExceptions = new Vector<Throwable>();

    if (e != null) {
        //String exceptionInfo = e.getClass().getName() + ": " + e.toString();
        _causeExceptions.addElement(e);
    }
}
public Enumeration<Throwable> getCauseExceptions() {
	   
	Vector<Throwable> tempVec = _causeExceptions;
	if (tempVec == null) tempVec = new Vector<Throwable>();

	return tempVec.elements();
}
public String getCauseExceptionsAsString() {
    String causeExceptionString = " ";

    if (_causeExceptions == null)
        causeExceptionString = " ";
    else {
        if (_causeExceptions.isEmpty() == true)
            causeExceptionString = " ";
        else {
            Enumeration<Throwable> list = _causeExceptions.elements();
            causeExceptionString = " ";
            while (list.hasMoreElements()) {
                causeExceptionString = 
                    causeExceptionString + "\n " + "" + (String) list.nextElement().toString(); 
            }
            causeExceptionString = "\n " + causeExceptionString;
        }
    }
    return causeExceptionString;
}
public Vector<Throwable> getCauseExceptionsAsVector() {	   
	return _causeExceptions;
}
public String getDetailDescription() {
    String causeExceptionString = "\n " + _errorCode + " " + _errorDescription;

    if (_causeExceptions != null) {
        if (_causeExceptions.isEmpty() == false) {
            Enumeration<Throwable> list = _causeExceptions.elements();

            while (list.hasMoreElements()) {
                Object e = list.nextElement();
                if (e instanceof AS2Exception) {
                    AS2Exception j2e = (AS2Exception) e;
                    causeExceptionString = causeExceptionString + j2e.getDetailDescription();
                }
            }
        }
    }
    return causeExceptionString;
}
public String getErrorCode () {
	return _errorCode.trim();
}
public String getErrorDescription () {
    if(_errorDescription!=null){
        _errorDescription =_errorDescription.replaceFirst("@",_field_one.toUpperCase());
    	_errorDescription =_errorDescription.replaceFirst("@",_field_two.toUpperCase());
    	_errorDescription =_errorDescription.replaceAll("_"," ");
    	_errorDescription =_errorDescription.toLowerCase();
    }
	return _errorDescription;
}
public java.lang.Object getErrorDetails() {
	return _errorDetails;
}
public String getErrorDetailsAsString() {
	if(_errorDetails!=null)
		return _errorDetails.toString();
	else
		return "";
}
public Date getOccuredDate ( ) {
	return _occuredDate;
}
public String getRecoveryAction () {
	return _recoveryAction;
}
public String getResourceBundle () {
	if(_resourceBundle == null)
		return "hr.as2.inf.common.exceptions.AS2DefaultResourceBundle";
	return _resourceBundle;
}
public int getSeverity() {
	return _severity;
}
public String getSourceObjectDescription() {
	return _sourceObjectDescription;
}
public String getTechnicalErrorDescription () {
	return _technicalErrorDescription;
}
public void printStackTrace() { 
    printStackTrace(System.err);
  }
  public synchronized void printStackTrace(java.io.PrintStream s) { 
    synchronized(s) {
      s.print(getClass().getName() + ": ");
      s.print(_stackTrace);
      for(int i = 0; i<_causeExceptions.size(); i++){
    	  _causeExceptions.elementAt(i).printStackTrace();
      }
    }
  }
  public void printStackTrace(java.io.PrintWriter s) { 
    synchronized(s) {
      s.print(getClass().getName() + ": ");
      s.print(_stackTrace);
    }
  }
public void rethrow() throws Exception {
    throw _originalException;
}
public void setCauseExceptions(Vector<Throwable> value) {

	_causeExceptions = value;

}
public void setErrorCode(String value) {
	if (value != null)
		_errorCode = value;
	try {
		ResourceBundle messages = ResourceBundle.getBundle(getResourceBundle());
		setErrorDescription(messages.getString(_errorCode));
	} catch (Exception e) {
		_errorDescription = value;
	}
}
public void setErrorDescription(String error_code, String field_one) {
    setErrorDescription(error_code, new Object[]{field_one});
}
public void setErrorDescription(String error_code, String field_one, String field_two) {
    setErrorDescription(error_code, new Object[]{field_one, field_two});
}
public void setErrorDescription(String error_code, Object[] fields) {
    _errorDescription = MessageFormat.format(java.util.ResourceBundle
            .getBundle(getResourceBundle()).getString(error_code), fields);
}
public void setErrorDescription(String value) {
	if(value != null)
		_errorDescription = value;
}
public void setErrorDetails(java.lang.Object value) {
	_errorDetails = value;
}
public void setOccuredDate (Date value) {
	if(value != null)
		_occuredDate = value;
}
public void setRecoveryAction(String value) {
	if(value != null)
		_recoveryAction = value;		
}
public void setResourceBundle(String value){
	if(value != null)
		_resourceBundle = value;
			
}
public void setSeverity(int value){
	_severity = value;
}
public void setSourceObjectDescription(String value) {
	if(value != null)
		_sourceObjectDescription = value;	
}
public void setTechnicalErrorDescription (String value) {
	if(value != null)
		_technicalErrorDescription = value;
}
public String toString() {
	
	return (
		super.toString() + 
		"\n Message:					" + getMessage() +
		"\n ErrorCode:					" + getErrorCode() +
		"\n ErrorDescription:			" + getErrorDescription() +
		"\n ResourceBundle:				" + getResourceBundle() +
		"\n TechnicalErrorDescription:	" + getTechnicalErrorDescription() +
		"\n Severity:					" + getSeverity() +
		"\n RecoveryAction:				" + getRecoveryAction() +
		"\n OccuredDate:				" + getOccuredDate() +
		"\n SourceObjectDescription:	" + getSourceObjectDescription() +
		"\n CauseExceptions:			" + getCauseExceptionsAsString() +
		"\n ErrorDetails:				" + getErrorDetails()+
		"\n");
}
}
