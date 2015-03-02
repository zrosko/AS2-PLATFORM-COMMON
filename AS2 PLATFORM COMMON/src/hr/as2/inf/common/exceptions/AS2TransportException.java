package hr.as2.inf.common.exceptions;

import java.util.Date;

public class AS2TransportException extends AS2Exception {
	private static final long serialVersionUID = 1L;

	public AS2TransportException() {
		super();
	}

	public AS2TransportException(String errorCode) {
		super(errorCode);
	}

	public AS2TransportException(String errorCode, String resourceBundle,
			String technicalErrorDescription, int severity,
			String recoveryAction, Date occuredDate) {
		super(errorCode, resourceBundle, technicalErrorDescription, severity,
				recoveryAction, occuredDate);
	}

	public String toString() {
		return super.toString();
	}
}
