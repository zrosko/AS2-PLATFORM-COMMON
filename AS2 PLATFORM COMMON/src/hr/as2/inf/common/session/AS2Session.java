package hr.as2.inf.common.session;

import hr.as2.inf.common.data.AS2Record;

public class AS2Session extends AS2Record {
	private static final long serialVersionUID = 1L;
	public final static String AS2_SESSION_SESSION_ID = "session_id";
	public final static String AS2_SESSION_USER = "username";
	public final static String AS2_SESSION_ROLE = "role";
	public final static String AS2_SESSION_LAST_ACCESSED_TIME = "last_accessed_time";
	public final static String AS2_SESSION_CREATION_TIME = "creation_time";
	public final static String AS2_SESSION_MAX_INACTIVE_INTERVAL = "max_inactive_interval";
	
	public static final String AS2_HANDLE_VALUE_LIST = "handle_value_list";

	public String getSessionId() {
		return get(AS2_SESSION_SESSION_ID);
	}

	public void setSessionId(String value) {
		set(AS2_SESSION_SESSION_ID, value);
	}

	public String getUserName() {
		return get(AS2_SESSION_USER);
	}

	public void setUserName(String value) {
		set(AS2_SESSION_USER, value);
	}
	public String getRole() {
		return get(AS2_SESSION_USER);
	}

	public void setRole(String value) {
		set(AS2_SESSION_USER, value);
	}

	public int getMaxInactiveInterval() {
		long end_time = getAsLong(AS2_SESSION_LAST_ACCESSED_TIME);
		long start_time = getAsLong(AS2_SESSION_CREATION_TIME);
		return (int) (end_time - start_time);
	}

	public void setMaxInactiveInterval(int value) {
		long start_time = System.currentTimeMillis();
		start_time = start_time + value;
		set(AS2_SESSION_MAX_INACTIVE_INTERVAL, start_time);
	}

	// Returns the time when this session was created, measured in milliseconds
	// since midnight January 1, 1970 GMT.
	public String getCreationTime() {
		return get(AS2_SESSION_CREATION_TIME);
	}

	public void setCreationTime(String value) {
		set(AS2_SESSION_CREATION_TIME, value);
	}

	public String getLastAccessedTime() {
		return get(AS2_SESSION_LAST_ACCESSED_TIME);
	}

	public void setLastAccessedTime(String value) {
		set(AS2_SESSION_LAST_ACCESSED_TIME, value);
	}

	public Object getAttribute(String name) {
		return getAsObject(name);
	}

	public void removeAttribute(String name) {
		delete(name);
	}

	public void setAttribute(String name, Object value) {
		set(name, value);
	}

	public String[] getAttributeNames() {
		return new String[1];
	}

	// Returns true if the client does not yet know about the session or if the
	// client chooses not to join the session.
	public boolean isNew() {
		return false;
	}

	// Invalidates this session then unbinds any objects bound to it.
	public void invalidate() {
		// TODO
	}
	//OLD
	public void setHandleValueList(boolean value){
	    set(AS2_HANDLE_VALUE_LIST,value);
	}
	public boolean handleValueList(){
	    return getAsBooleanOrTrue(AS2_HANDLE_VALUE_LIST);
	}
}
