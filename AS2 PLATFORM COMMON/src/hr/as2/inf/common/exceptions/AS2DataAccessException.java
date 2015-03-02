package hr.as2.inf.common.exceptions;

import java.util.Date;

/**
 * Data Access layer exception. All data access objects should catch the other
 * types of the exceptions and throw the J2EEDataAccessException. The other type
 * of the exception should be added to the
 * AS2DataAccessException.addCauseException(e).
 * 
 * @version 2.0
 * @author zrosko@gmail.com
 * TODO add error code to aspect.
 */
public class AS2DataAccessException extends AS2Exception {
	private static final long serialVersionUID = 1L;

	public AS2DataAccessException() {
		super();
	}

	public AS2DataAccessException(Exception e) {
		super(e);
	}

	public AS2DataAccessException(String errorCode) {
		super(errorCode);
	}

	public AS2DataAccessException(String errorCode, String resourceBundle,
			String technicalErrorDescription, int severity,
			String recoveryAction, Date occuredDate) {
		super(errorCode, resourceBundle, technicalErrorDescription, severity,
				recoveryAction, occuredDate);
	}

	public String toString() {
		return super.toString();
	}
}
