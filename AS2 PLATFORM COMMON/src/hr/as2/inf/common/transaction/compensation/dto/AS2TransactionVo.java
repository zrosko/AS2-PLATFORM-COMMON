package hr.as2.inf.common.transaction.compensation.dto;

import hr.as2.inf.common.data.AS2Record;

import java.util.Calendar;

public class AS2TransactionVo extends AS2Record {
	private static final long serialVersionUID = 1L;
	public static String J2EE_TRANSACTION__ROW_ID = "row_id";
    public static String J2EE_TRANSACTION__TRANSACTION_ID = "transaction_id";
    public static String J2EE_TRANSACTION__TIMESTAMP = "timestamp";
    public static String J2EE_TRANSACTION__END_TIMESTAMP = "end_timestamp";
    public static String J2EE_TRANSACTION__SOURCE_SYSTEM = "source_system";
    public static String J2EE_TRANSACTION__TARGET_SYSTEM = "target_system";
    public static String J2EE_TRANSACTION__USER_NAME = "user_name";
    public static String J2EE_TRANSACTION__THREAD_NAME = "thread_name";
    public static String J2EE_TRANSACTION__SOURCE_ADDRESS = "source_address";
    public static String J2EE_TRANSACTION__REPLAY_QUEUE = "replay_queue";
    public static String J2EE_TRANSACTION__SEQUENCE = "sequence";
    public static String J2EE_TRANSACTION__SEQUENCE_SIZE = "sequence_size";
    public static String J2EE_TRANSACTION__XML = "xml";
    public static String J2EE_TRANSACTION__STATUS = "status";
    public static String J2EE_TRANSACTION__ERROR = "error";
    public static String J2EE_TRANSACTION__ERROR_XML = "error_xml";
    public static String J2EE_TRANSACTION__DELETED = "deleted";
    public AS2TransactionVo() {
        super();
    }
    public AS2TransactionVo(AS2Record value) {
        super(value);
    }
    public String getRowId() {
        return getAsString(J2EE_TRANSACTION__ROW_ID);
    }
    public String getTransactionId() {
        return getAsString(J2EE_TRANSACTION__TRANSACTION_ID);
    }
    public Calendar getTimestamp() {
        return getAsCalendar(J2EE_TRANSACTION__TIMESTAMP);
    }
    public Calendar getEndTimestamp() {
        return getAsCalendar(J2EE_TRANSACTION__END_TIMESTAMP);
    }
    public String getSourceSystem() {
        return getAsString(J2EE_TRANSACTION__SOURCE_SYSTEM);
    }
    public String getTargetSystem() {
        return getAsString(J2EE_TRANSACTION__TARGET_SYSTEM);
    }
    public String getUserName() {
        return getAsString(J2EE_TRANSACTION__USER_NAME);
    }
    public String getThreadName() {
        return getAsString(J2EE_TRANSACTION__THREAD_NAME);
    }
    public String getSourceAddress() {
        return getAsString(J2EE_TRANSACTION__SOURCE_ADDRESS);
    }
    public String getReplayQueue() {
        return getAsString(J2EE_TRANSACTION__REPLAY_QUEUE);
    }
    public String getSequence() {
        return getAsString(J2EE_TRANSACTION__SEQUENCE);
    }
    public String getSequenceSize() {
        return getAsString(J2EE_TRANSACTION__SEQUENCE_SIZE);
    }
    public String getXml() {
        return getAsString(J2EE_TRANSACTION__XML);
    }
    public String getStatus() {
        return getAsString(J2EE_TRANSACTION__STATUS);
    }
    public String getError() {
        return getAsString(J2EE_TRANSACTION__ERROR);
    }
    public String getErrorXml() {
        return getAsString(J2EE_TRANSACTION__ERROR_XML);
    }
    public String getDeleted() {
        return getAsString(J2EE_TRANSACTION__DELETED);
    }
    public void setRowId(String value) {
        set(J2EE_TRANSACTION__ROW_ID, value);
    }
    public void setTransactionId(String value) {
        set(J2EE_TRANSACTION__TRANSACTION_ID, value);
    }
    public void setTimestamp(Calendar value) {
        setCalendarAsDateString(value, J2EE_TRANSACTION__TIMESTAMP);
    }
    public void setEndTimestamp(Calendar value) {
        setCalendarAsDateString(value, J2EE_TRANSACTION__END_TIMESTAMP);
    }
    public void setSourceSystem(String value) {
        set(J2EE_TRANSACTION__SOURCE_SYSTEM, value);
    }
    public void setTargetSystem(String value) {
        set(J2EE_TRANSACTION__TARGET_SYSTEM, value);
    }
    public void setUserName(String value) {
        set(J2EE_TRANSACTION__USER_NAME, value);
    }
    public void setThreadName(String value) {
        set(J2EE_TRANSACTION__THREAD_NAME, value);
    }
    public void setSourceAddress(String value) {
        set(J2EE_TRANSACTION__SOURCE_ADDRESS, value);
    }
    public void setReplayQueue(String value) {
        set(J2EE_TRANSACTION__REPLAY_QUEUE, value);
    }
    public void setSequence(String value) {
        set(J2EE_TRANSACTION__SEQUENCE, value);
    }
    public void setSequenceSize(String value) {
        set(J2EE_TRANSACTION__SEQUENCE_SIZE, value);
    }
    public void setXml(String value) {
        set(J2EE_TRANSACTION__XML, value);
    }
    public void setStatus(String value) {
        set(J2EE_TRANSACTION__STATUS, value);
    }
    public void setError(String value) {
        set(J2EE_TRANSACTION__ERROR, value);
    }
    public void setErrorXml(String value) {
        set(J2EE_TRANSACTION__ERROR_XML, value);
    }
    public void setDeleted(String value) {
        set(J2EE_TRANSACTION__DELETED, value);
    }
}