package hr.as2.inf.common.exceptions;

import java.util.ArrayList;
import java.util.List;

public class AS2ValidationException extends AS2Exception {

	private static final long serialVersionUID = 1L;
	/**
	 * List of error/warning messages.
	 */
	private List<String> errorList;

	public AS2ValidationException(String errorCode) {
		super(errorCode);
	}

	public AS2ValidationException(Object[] initArgs) {
		errorList = new ArrayList<String>();
	}

	public void addMessage(String message) {
		errorList.add(message);
	}
}
