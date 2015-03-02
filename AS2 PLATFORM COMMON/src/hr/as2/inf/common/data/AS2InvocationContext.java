package hr.as2.inf.common.data;


@SuppressWarnings("serial")
public class AS2InvocationContext extends AS2Record {
	private static final String REMOTE_ADDR = "@@remote_addr";
	private static final String SESSION_ID = "@@session_id";
	private static final String THREAD_ID = "@@thread_id";
	private static final String START_TIME = "@@start_time";
	private static final String USER_ID = "@@user_id";
	private static final String REMOTE_OBJECT = "@@remote_object";
	private static final String REMOTE_METHOD = "@@remote_method";
	/* Value list info */
    public final static String HAS_MORE_RESULTS = "@@has_more_results";
    public final static String FORWARD_ACTION = "@@forward_action";
    public final static String BACKWARD_ACTION = "@@backward_action";
    public final static String REFRESH_ACTION = "@@refresh_action";
    public final static String POSITION = "@@POSITION";
    public final static String SIZE = "@@size";
    public final static String MAX = "@@max";
	/* Getters*/
    public void setRemoteAddr(String value){
		set(REMOTE_ADDR, value);
	}
	public void setSessionId(String value){
		set(SESSION_ID, value);
	}
	public void setThreadId(String value){
		set(THREAD_ID, value);
	}
	public void setStartTime(String value){
		set(START_TIME, value);
	}
	public void setUserId(String value){
		set(USER_ID, value);
	}
	public void setRemoteObject(String value){
		set(REMOTE_OBJECT, value);
	}
	public void setRemoteMethod(String value){
		set(REMOTE_METHOD, value);
	}
	/* Value list info getters */
	public boolean hasMoreResults(){
        return getAsBooleanOrFalse(HAS_MORE_RESULTS);
    }
    public boolean forwardAction(){
        return getAsBooleanOrFalse(FORWARD_ACTION);
    }
    public boolean backwardAction(){
        return getAsBooleanOrFalse(BACKWARD_ACTION);
    }
    public boolean refreshAction(){
        return getAsBooleanOrFalse(REFRESH_ACTION);
    }
    public int getPosition(){
        int position = getAsInt(POSITION);
        if(position == 0)
            return 1;
        else
            return position;
    }
    public int getSize(){
        return getAsInt(SIZE);
    }    
    public int getMax(){
        return getAsInt(MAX);
    }
	/* Setters*/
    public String getRemoteAddr(){
    	return getAsString(REMOTE_ADDR);
	}
	public String getSessionId(){
		return getAsString(SESSION_ID);
	}
	public String getThreadId(){
		return getAsString(THREAD_ID);
	}
	public String getStartTime(){
		return getAsString(START_TIME);
	}
	public String getUserId(){
		return getAsString(USER_ID);
	}
	public String getRemoteObject(){
		return getAsString(REMOTE_OBJECT);
	}
	public String getRemoteMethod(){
		return getAsString(REMOTE_METHOD);
	}
	/* Value list info setters */
	public void setHasMoreResults(boolean value){
        set(HAS_MORE_RESULTS,value);
    }
    public void setForwardAction(boolean value){
        set(FORWARD_ACTION,value);
    }
    public void setBackwardAction(boolean value){
        set(BACKWARD_ACTION, value);
    }
    public void setRefreshAction(boolean value){
        set(REFRESH_ACTION, value);
    }
    public void setPosition(int value){
        set(POSITION, value);
    }
    public void setSize(int value){
        set(SIZE, value);
    }
    public void setMax(int value){
        set(MAX, value);
    }
    public void reset(){
        setPosition(0);
        setSize(0);
        setMax(0);
        setHasMoreResults(false);
        resetActions();
    }
    public void resetActions(){
        set(FORWARD_ACTION,"false");
        set(BACKWARD_ACTION,"false");
        set(REFRESH_ACTION,"false");
    }
}
