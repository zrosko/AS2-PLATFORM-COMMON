package hr.as2.inf.common.exceptions;


/**
 * Defines common exception for all the
 * data source connection's exceptions. 
 *
 * @version 1.0 
 * @author 	Zdravko Rosko
 */
public class AS2ConnectionException extends AS2Exception {
	private static final long serialVersionUID = 1L;
	/**
	 * IP address or host name of the data source being accessed.
	 */
	public String _host;
	/**
	 * User id to access a data source (JDBC, MQ, ...).
	 */
	public String _user;
	/**
	 * Password to access a data source (JDBC, MQ, ...).
	 */
	public String _password;
public AS2ConnectionException() {
	super();
}
public AS2ConnectionException(String errorCode) {
	super(errorCode);
}
public AS2ConnectionException(String host, String user, String password) {
	super();
	_host = host;
	_user = user;
	_password = password;
}
public AS2ConnectionException(
	String errorCode, 
	String resourceBundle, 
	String technicalErrorDescription, 
	int severity, 
	String recoveryAction, 
	java.util.Date occuredDate, 
	String host, 
	String user, 
	String password) {
	super(
		errorCode, 
		resourceBundle, 
		technicalErrorDescription, 
		severity, 
		recoveryAction, 
		occuredDate); 
	_host = host;
	_user = user;
	_password = password;
}
public String getHost() {
	return _host;
}
public String getPassword() {
	return _password;
}
public String getUser() {
	return _user;
}
public void setHost(String host) {
	_host = host;
}
public void setPassword(String password) {
	_password = password;
}
public void setUser(String user) {
	_user = user;
}
public String toString() {
	String s = getClass().getName();
	String message = super.toString() 
	+ "\n" + "Host=" + _host 
	+ "\n" + "User=" + _user; 
	//+ "\n" + "Password=" + _password /** Do not print password !**/
	//+ "\n"; 
	return (message != null) ? (s + ": " + message) : s;	
}
}
