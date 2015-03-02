package hr.as2.inf.common.transaction.compensation.dto;

import hr.as2.inf.common.data.AS2Record;

public class AS2TransactionStepVo extends AS2Record {
	private static final long serialVersionUID = 1L;
	public static String J2EE_TRANSACTION_STEP__STEP_ID = "step_id";
    public static String J2EE_TRANSACTION_STEP__TRANSACTION_ID = "transaction_id";
    public static String J2EE_TRANSACTION_STEP__SOURCE_SUBSYSTEM = "source_subsystem";
    public static String J2EE_TRANSACTION_STEP__SEQUENCE_NUMBER = "sequence_number";
    public static String J2EE_TRANSACTION_STEP__TARGET_SERVICE = "target_service";
    public static String J2EE_TRANSACTION_STEP__XML = "xml";
    public static String J2EE_TRANSACTION_STEP__STATUS = "status";
    public static String J2EE_TRANSACTION_STEP__ERROR = "error";
    public static String J2EE_TRANSACTION_STEP__ERROR_XML = "error_xml";
    public static String J2EE_TRANSACTION_STEP__DELETETD = "deletetd";

    public AS2TransactionStepVo() {
        super();
    }
    public AS2TransactionStepVo(AS2Record value) {
        super(value);
    }
    public String getStepId() {
    	return getAsString(J2EE_TRANSACTION_STEP__STEP_ID);
    }
    public String getTransactionId() {
    	return getAsString(J2EE_TRANSACTION_STEP__TRANSACTION_ID);
    }
    public String getSourceSubsystem() {
    	return getAsString(J2EE_TRANSACTION_STEP__SOURCE_SUBSYSTEM);
    }
    public String getSequenceNumber() {
    	return getAsString(J2EE_TRANSACTION_STEP__SEQUENCE_NUMBER);
    }
    public String getTargetService() {
    	return getAsString(J2EE_TRANSACTION_STEP__TARGET_SERVICE);
    }
    public String getXml() {
    	return getAsString(J2EE_TRANSACTION_STEP__XML);
    }
    public String getStatus() {
    	return getAsString(J2EE_TRANSACTION_STEP__STATUS);
    }
    public String getError() {
    	return getAsString(J2EE_TRANSACTION_STEP__ERROR);
    }
    public String getErrorXml() {
    	return getAsString(J2EE_TRANSACTION_STEP__ERROR_XML);
    }
    public String getDeletetd() {
    	return getAsString(J2EE_TRANSACTION_STEP__DELETETD);
    }
    public void setStepId(String value) {
    	set(J2EE_TRANSACTION_STEP__STEP_ID, value);
    }
    public void setTransactionId(String value) {
    	set(J2EE_TRANSACTION_STEP__TRANSACTION_ID, value);
    }
    public void setSourceSubsystem(String value) {
    	set(J2EE_TRANSACTION_STEP__SOURCE_SUBSYSTEM, value);
    }
    public void setSequenceNumber(String value) {
    	set(J2EE_TRANSACTION_STEP__SEQUENCE_NUMBER, value);
    }
    public void setTargetService(String value) {
    	set(J2EE_TRANSACTION_STEP__TARGET_SERVICE, value);
    }
    public void setXml(String value) {
    	set(J2EE_TRANSACTION_STEP__XML, value);
    }
    public void setStatus(String value) {
    	set(J2EE_TRANSACTION_STEP__STATUS, value);
    }
    public void setError(String value) {
    	set(J2EE_TRANSACTION_STEP__ERROR, value);
    }
    public void setErrorXml(String value) {
    	set(J2EE_TRANSACTION_STEP__ERROR_XML, value);
    }
    public void setDeletetd(String value) {
    	set(J2EE_TRANSACTION_STEP__DELETETD, value);
    }
}