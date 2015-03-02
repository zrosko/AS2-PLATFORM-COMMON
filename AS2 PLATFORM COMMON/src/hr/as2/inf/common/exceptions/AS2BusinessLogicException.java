package hr.as2.inf.common.exceptions;

import java.util.Date;
/**
 * Defines common exception for all business logic layer exceptions. 
 * Business Logic layer exception. All business objects may cath the other types
 * of the exceptions and throw the J2EEBusinessLogicException. The other type of the
 * exception shoud be added to the J2EEBusinessLogicException.addCauseException(e).
 *
 * @version 1.0 
 * @author 	Zdravko Rosko
 */
public class AS2BusinessLogicException extends AS2Exception {
	private static final long serialVersionUID = 1L;
public AS2BusinessLogicException() {
	super();
}
public AS2BusinessLogicException(String errorCode) {
	super(errorCode);
}
public AS2BusinessLogicException(
    String errorCode, 
    String resourceBundle, 
    String technicalErrorDescription, 
    int severity, 
    String recoveryAction, 
    Date occuredDate) {
    super(
        errorCode, 
        resourceBundle, 
        technicalErrorDescription, 
        severity, 
        recoveryAction, 
        occuredDate); 
}
public String toString() {
	return super.toString();
}
}
