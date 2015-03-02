package hr.as2.inf.common.admin.dto;

import hr.as2.inf.common.data.AS2Record;

public class AS2ConfigurationVo extends AS2Record {
	private static final long serialVersionUID = 1L;
	public static String CONFIGURATION__QUERY_CACHING_IND = "QUERY_CACHING_IND";
    public static String CONFIGURATION__SERVER_CACHING_IND = "SERVER_CACHING_IND";
    public static String CONFIGURATION__CLIENT_CACHING_IND = "CLIENT_CACHING_IND";
    public static String CONFIGURATION__CACHE_CLEANER_REPEAT_TIME = "CACHE_CLEANER_REPEAT_TIME";
    public static String CONFIGURATION__TRACE_ON = "TRACE_ON";
    public static String CONFIGURATION__TRACE_LEVEL = "TRACE_LEVEL"; 
    public static String CONFIGURATION__ONE_CACHE_TIME = "ADM_CACHE_TIME"; //TODO dati drugo ime
    public static String CONFIGURATION__TWO_CACHE_TIME = "SDE_CACHE_TIME"; //TODO dati drugo ime   
     
    public AS2ConfigurationVo()	{
		super();
	}
	public AS2ConfigurationVo(AS2Record value)	{
		super(value);
	}
	public String getQueryCachingInd() {
		return getAsString(CONFIGURATION__QUERY_CACHING_IND);
	}
	public String getServerCachingInd() {
		return getAsString(CONFIGURATION__SERVER_CACHING_IND);
	}
	public String getClientCachingInd() {
		return getAsString(CONFIGURATION__CLIENT_CACHING_IND);
	}
	public String getCacheCleanerRepeatTime() {
		return getAsString(CONFIGURATION__CACHE_CLEANER_REPEAT_TIME);
	}
	public String getTraceOn() {
		return getAsString(CONFIGURATION__TRACE_ON);
	}
	public String getTraceLevel() {
		return getAsString(CONFIGURATION__TRACE_LEVEL);
	}	
	public String getOneCacheTime() {
		return getAsString(CONFIGURATION__ONE_CACHE_TIME);
	}
	public String getTwoCacheTime() {
		return getAsString(CONFIGURATION__TWO_CACHE_TIME);
	}
	//setters
	public void setQueryCachingInd(String value) {
		set(CONFIGURATION__QUERY_CACHING_IND, value);
	}
	public void setServerCachingInd(String value) {
		set(CONFIGURATION__SERVER_CACHING_IND, value);
	}
	public void setClientCachingInd(String value) {
		set(CONFIGURATION__CLIENT_CACHING_IND, value);
	}
	public void setCacheCleanerRepeatTime(String value) {
		set(CONFIGURATION__CACHE_CLEANER_REPEAT_TIME, value);
	}
	public void setTraceOn(String value) {
		set(CONFIGURATION__TRACE_ON, value);
	}
	public void setTraceLevel(String value) {
		set(CONFIGURATION__TRACE_LEVEL, value);
	}	
	public void setOneCacheTime(String value) {
		set(CONFIGURATION__ONE_CACHE_TIME, value);
	}
	public void setTwoCacheTime(String value) {
		set(CONFIGURATION__TWO_CACHE_TIME, value);
	}
}
