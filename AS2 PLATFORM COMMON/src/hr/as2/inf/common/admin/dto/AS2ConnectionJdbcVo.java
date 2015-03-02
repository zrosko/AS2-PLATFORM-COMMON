package hr.as2.inf.common.admin.dto;

import hr.as2.inf.common.data.AS2Record;

public class AS2ConnectionJdbcVo extends AS2ConnectionVo {
	private static final long serialVersionUID = 1L;
	public static String _prefix = "hr.as2.inf.server.connection.jdbc.AS2ConnectionManagerJDBC.";    
    public static String CONNECTION__DBUSED = _prefix+"DBUSED";
    public static String CONNECTION__DBNAME = _prefix+"DBNAME";
    public static String CONNECTION__SCHEMA = _prefix+"SCHEMA";    
    public static String CONNECTION__DRIVER = _prefix+"DRIVER";
    public static String CONNECTION__DBURL = _prefix+"DBURL"; 
    public static String CONNECTION__MAXROWS = _prefix+"MAXROWS";    
    
    public AS2ConnectionJdbcVo()	{
		super();
	}
	public AS2ConnectionJdbcVo(AS2Record value)	{
		super(value);
	}
	public String getDbused() {
		return getAsString(CONNECTION__DBUSED);
	}
	public String getDbname() {
		return getAsString(CONNECTION__DBNAME);
	}
	public String getSchema() {
		return getAsString(CONNECTION__SCHEMA);
	}
	public String getDriver() {
		return getAsString(CONNECTION__DRIVER);
	}
	public String getDburl() {
		return getAsString(CONNECTION__DBURL);
	}	
	public String getMaxrows() {
		return getAsString(CONNECTION__MAXROWS);
	}

	//setters
	public void setDbused(String value) {
		set(CONNECTION__DBUSED, value);
	}
	public void setDbname(String value) {
		set(CONNECTION__DBNAME, value);
	}
	public void setSchema(String value) {
		set(CONNECTION__SCHEMA, value);
	}
	public void setDriver(String value) {
		set(CONNECTION__DRIVER, value);
	}
	public void setDburl(String value) {
		set(CONNECTION__DBURL, value);
	}	
	public void setMaxrows(String value) {
		set(CONNECTION__MAXROWS, value);
	}
}
