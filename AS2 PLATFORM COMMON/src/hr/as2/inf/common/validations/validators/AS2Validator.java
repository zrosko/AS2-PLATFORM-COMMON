package hr.as2.inf.common.validations.validators;

import hr.as2.inf.common.exceptions.AS2Exception;

/**
* Validator is a base interface for all objects that need to be evaluated. By
* default, all classes should return true if they don't need validation. TIP:
* Check if validator interface needs to retrive a list of messages that are the
* result of isValid method call! A code could than look like: if
* (!validatorInst.isValid()) { List validatorInst.getValidationMessages();
* Iterator it = validatorList.iterator(); while (it.hasNext()) {
* displayMessage((String)it.next(); } } ... Created by CU {BS} <br>
* Created on 2004.11.05 <br>
* Version: 1.00 <br>
* <br>
* Last modification date: <br>
* Modified by: <br>
*  */
public class AS2Validator {
	/**
     * Error message that is included in an {@link AS2Exception} if
     * such is thrown.
     */
    private String errorMessage;
    
    public AS2Validator(){
    	
    }
    public AS2Validator(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    /**
     * Returns the message to be included in the exception in case the value
     * does not validate.
     */
    public String getErrorMessage() {
        return errorMessage;
    }
    /**
     * Sets the message to be included in the exception in case the value does
     * not validate. The exception message is typically shown to the end user.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public void validate(Object value) throws AS2Exception {
        if (!isValid(value)) {
            String message = getErrorMessage().replace("{0}", String.valueOf(value));
            throw new AS2Exception(message);
        }
    }
    protected boolean isValid(Object value){
        if (value == null) {
            return true;
        } else
        	return false;
    }
}
