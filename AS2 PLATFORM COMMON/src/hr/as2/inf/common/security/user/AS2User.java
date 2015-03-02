package hr.as2.inf.common.security.user;

import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.data.AS2RecordList;
import hr.as2.inf.common.security.AS2SecurityConstants;
import hr.as2.inf.common.security.encoding.AS2Base64;
import hr.as2.inf.common.types.AS2Date;

import java.util.Date;
/**
 * Application user.
 */
public class AS2User extends AS2Record implements AS2SecurityConstants {
	private static final long serialVersionUID = 1L;
	public static final String APLIKACIJA = "aplikacija"; 
	public static final String USER_ID = "user_id"; 	
	public static final String USER_NAME = "user_name"; 
	public static final String PASSWORD = "password"; 
	public static final String NEW_PASSWORD = "new_password"; 
	public static final String FIRST_NAME = "first_name"; 
	public static final String LAST_NAME = "last_name"; 
	public static final String VALID_IND = "valid_ind"; 
	public static final String LOGIN_DATE = "login_date"; 
	public static final String LOGIN_ERROR = "login_error"; 
	public static final String DEPARTMENT_ID = "id_sluzbe"; 	
	public static final String VALID_USER = "valid_user"; 
	public static final String ROLE_ID = "role_id"; 	
	public static final String PROFIT_CENTER = "profitni_centar"; 
	public static final String SECURITY_LEVEL = "razina"; 
	public static final String SECURITY_LEVEL_GEOGRAFY = "razina_geografy";
	public static final String BRANCH = "org_jedinica"; 
	public static final String FUNCTIONS ="@@FUNCTIONS";

	public AS2User(){
		super();
	}

	public AS2User(String user_id) {
		this(user_id, null, false);
	}

	public AS2User(String user_id, String password) {
		this(user_id, password, false);
	}

	public AS2User(String user_id, String password, boolean valid) {
		if (user_id == null){
			user_id = ""; 
		}
		if (password == null){
			password = "";
		}
		setUserId(user_id);
		setPassword(password);
		setValid(valid);
	}

	public AS2User(AS2Record value){
		super(value);
	}

	public String getLoginError(){
		return get(LOGIN_ERROR);
	}

	public String getPassword() {
		return AS2Base64.decode(get(PASSWORD));
	}
	public String getNewPassword()	{
		return get(NEW_PASSWORD);
	}
	public String getUserName()	{
		return get(USER_NAME);
	}
	public String getUserId()	{
		return get(USER_ID);
	}
	public String getUserDepartment(){
	    return get(DEPARTMENT_ID);
	}
	public String getUserBranch(){
	    return get(BRANCH);
	}
	public String getUserRole(){
	    return get(ROLE_ID);
	}
	public void setUserRole(String value){
	    set(ROLE_ID, value);
	}
	public void setUserDepartment(String value){
	    set(DEPARTMENT_ID, value);
	}
	public boolean isValidInd()	{
		String valid =  get(VALID_IND);
		return !valid.equalsIgnoreCase("N");
	}
	public boolean isValid(){
		return getAsBooleanOrFalse(VALID_USER);
	}
	public void setLoginError(String value)	{
		set(LOGIN_ERROR, value);
	}
	public void setPassword(String value) {
		set(PASSWORD, AS2Base64.encode(value));

	}
	public void setNewPassword(String value) {
		set(NEW_PASSWORD, value);

	}
	public void setProfitCenter(String value){
		set(PROFIT_CENTER, value);
	}
	public String getPodrucje(){
	    if (getAsObject("podrucje")==null)
	            return getProfitCenter();
	    else
	        return get("podrucje");
	}
	public String getProfitCenter(){
	    return get(PROFIT_CENTER);
	}
	public String getSecurityLevel(){
	    return get(SECURITY_LEVEL);
	}	
	public String getSecurityLevelGeografy(){
	    return get(SECURITY_LEVEL_GEOGRAFY);
	}
	public void setApplication(String value){
		set(APLIKACIJA, value);
	}
	public String getApplication(){
	    return get(APLIKACIJA);
	}
	public void setUserId(String value)
	{
		set(USER_ID, value);
	}
	public void setUserName(String value)
	{
		set(USER_NAME, value);
	}
	public void setValidInd(String value)
	{
		set(VALID_IND, value);
	}
	public void setValid(boolean value)
	{
		set(VALID_USER, value);
	}
	public void setLoginDate(Date loginDate)
	{
		set(LOGIN_DATE, AS2Date.convert(loginDate));
	}

	public void setLoginDate(String loginDate)
	{
		set(LOGIN_DATE, loginDate);
	}

	public Date getLoginDate()
	{
		return getAsSqlDate(LOGIN_DATE);
	}

	public String getLoginDateAsString()
	{
		return getAsStringOrEmpty(LOGIN_DATE);
	}
	public int numberOfFunctions(){	    
	    AS2RecordList rs = getFunctions();
	    if(rs!=null)
	        return rs.size();
	    else
	        return 99;
	}
	public AS2RecordList getFunctions(){
	    return (AS2RecordList) getAsObject(AS2User.FUNCTIONS);
	}
	public boolean isFunctionAllowed(int function_id){
	    boolean ok = false;
	    AS2RecordList rs = (AS2RecordList) getAsObject(AS2User.FUNCTIONS);
	    if(rs==null)
	        return true;
	    for(AS2Record vo: rs.getRows()){
	    	if(vo.getAsInt(FUNCTION__FUNCTION_ID) == function_id)
	            return true;
	    }
	    return ok;	    
	}
	public String getFunctionTransactions(int function_id){
	    AS2RecordList rs = (AS2RecordList) getAsObject(AS2User.FUNCTIONS);
	    if(rs==null)
	        return "";
	    for(AS2Record vo: rs.getRows()){
	        if(vo.getAsInt(FUNCTION__FUNCTION_ID) == function_id)
	            return vo.get(ROLE_FUNCTION__TRANSACTIONS);
	    }
	    return "";	    
	}
	public void setFunctionSecurityLevel(int function_id){
        AS2RecordList rs = (AS2RecordList) getAsObject(AS2User.FUNCTIONS);
        if(rs==null){//ako ne postoji postavljena razina korisnik vidi SVE
            set(ROLE_FUNCTION__SECURITY_LEVEL, ROLE_FUNCTION__SECURITY_LEVEL_ONE);
            return;
        }
        for(AS2Record vo: rs.getRows()){
            if(vo.getAsInt(FUNCTION__FUNCTION_ID) == function_id){
                set(ROLE_FUNCTION__SECURITY_LEVEL, vo.get(ROLE_FUNCTION__SECURITY_LEVEL));
                return;
            }
        }        
        set(ROLE_FUNCTION__SECURITY_LEVEL, ROLE_FUNCTION__SECURITY_LEVEL_ONE);  
    }

	public String toString(){ 
		String s = getClass().getName();
		String message = "";
		message = super.toString();
		return (message != null) ? (s + ": " + message) : s;
	}
}
