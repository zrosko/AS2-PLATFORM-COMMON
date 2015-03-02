package hr.as2.inf.common.admin.dto;

import hr.as2.inf.common.data.AS2Record;

public class AS2ConnectionVo extends AS2Record {
	private static final long serialVersionUID = 1L;
	public static String _prefix = "hr.as2.inf.server.connection.AS2ConnectionManager.";
    public static String CONNECTION__USER = _prefix+"USER";
    public static String CONNECTION__PASSWORD = _prefix+"PASSWORD";
    public static String CONNECTION__ENCODE_BASE64 = _prefix+"ENCODE_BASE64";
    public static String CONNECTION__HOST = _prefix+"HOST";
    public static String CONNECTION__PORT = _prefix+"PORT"; 
    public static String CONNECTION__MAX_CONNECTIONS = _prefix+"MAX_CONNECTIONS";    
    public static String CONNECTION__APS_POOL = _prefix+"APS_POOL";    
    public static String CONNECTION__USE_POOL = _prefix+"USE_POOL";
    public static String CONNECTION__APS_POOL_NAME = _prefix+"APS_POOL_NAME";    
    public static String CONNECTION__PING_INTERVAL = _prefix+"PING_INTERVAL";
    public static String CONNECTION__CONNECT_RETRY_SLEEP = _prefix+"CONNECT_RETRY_SLEEP";    
    public static String CONNECTION__CONNECT_RETRY = _prefix+"CONNECT_RETRY";
    public static String CONNECTION__PING_COMMAND = _prefix+"PING_COMMAND";
    public static String CONNECTION__KEEP_ALIVE_TIMEOUT = _prefix+"KEEP_ALIVE_TIMEOUT";
    public static String CONNECTION__MIN_CONNECTIONS = _prefix+"MIN_CONNECTIONS";
    public static String CONNECTION__WATCH_INTERVAL = _prefix+"WATCH_INTERVAL";
    
    public AS2ConnectionVo()	{
		super();
	}
	public AS2ConnectionVo(AS2Record value)	{
		super(value);
	}
	public String getUser() {
		return getAsString(CONNECTION__USER);
	}
	public String getPassword() {
		return getAsString(CONNECTION__PASSWORD);
	}
	public String getEncodeBase64() {
		return getAsString(CONNECTION__ENCODE_BASE64);
	}
	public String getHost() {
		return getAsString(CONNECTION__HOST);
	}
	public String getPort() {
		return getAsString(CONNECTION__PORT);
	}	
	public String getMaxConnections() {
		return getAsString(CONNECTION__MAX_CONNECTIONS);
	}
	public String getApsPool() {
		return getAsString(CONNECTION__APS_POOL);
	}
	public String getUsePool() {
		return getAsString(CONNECTION__USE_POOL);
	}
	public String getPoolName() {
		return getAsString(CONNECTION__APS_POOL_NAME);
	}	
	public String getPingInterval() {
		return getAsString(CONNECTION__PING_INTERVAL);
	}
	public String getConnectRetrySleep() {
		return getAsString(CONNECTION__CONNECT_RETRY_SLEEP);
	}
	public String getPingCommand() {
		return getAsString(CONNECTION__PING_COMMAND);
	}
	public String getKeepAliveTimeout() {
		return getAsString(CONNECTION__KEEP_ALIVE_TIMEOUT);
	}
	public String getMinConnections() {
		return getAsString(CONNECTION__MIN_CONNECTIONS);
	}
	public String getWatchInterval() {
		return getAsString(CONNECTION__WATCH_INTERVAL);
	}
	//setters
	public void setUser(String value) {
		set(CONNECTION__USER, value);
	}
	public void setPassword(String value) {
		set(CONNECTION__PASSWORD, value);
	}
	public void setEncodeBase64(String value) {
		set(CONNECTION__ENCODE_BASE64, value);
	}
	public void setHost(String value) {
		set(CONNECTION__HOST, value);
	}
	public void setPort(String value) {
		set(CONNECTION__PORT, value);
	}	
	public void setMaxConnections(String value) {
		set(CONNECTION__MAX_CONNECTIONS, value);
	}
	public void setApsPool(String value) {
		set(CONNECTION__APS_POOL, value);
	}
	public void setUsePool(String value) {
		set(CONNECTION__USE_POOL, value);
	}
	public void setApsPoolName(String value) {
		set(CONNECTION__APS_POOL_NAME, value);
	}
	public void setPingInterval(String value) {
		set(CONNECTION__PING_INTERVAL, value);
	}	
	public void setConnectRetrySleep(String value) {
		set(CONNECTION__CONNECT_RETRY_SLEEP, value);
	}
	public void setPingCommand(String value) {
		set(CONNECTION__PING_COMMAND, value);
	}
	public void setKeepAliveTimeout(String value) {
		set(CONNECTION__KEEP_ALIVE_TIMEOUT, value);
	}
	public void setMinConnections(String value) {
		set(CONNECTION__MIN_CONNECTIONS, value);
	}
	public void setWatchInterval(String value) {
		set(CONNECTION__WATCH_INTERVAL, value);
	}
}
