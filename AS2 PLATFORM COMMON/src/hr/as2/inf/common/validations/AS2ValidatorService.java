package hr.as2.inf.common.validations;
import hr.as2.inf.common.core.AS2Context;
import hr.as2.inf.common.core.AS2Helper;
import hr.as2.inf.common.data.AS2Record;
import hr.as2.inf.common.exceptions.AS2Exception;
import hr.as2.inf.common.exceptions.AS2ValidationException;
import hr.as2.inf.common.logging.AS2Trace;
import hr.as2.inf.common.validations.validators.AS2CreditCardValidator;
import hr.as2.inf.common.validations.validators.AS2DateValidator;
import hr.as2.inf.common.validations.validators.AS2ModuleValidator;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Properties;
import java.util.StringTokenizer;

public class AS2ValidatorService {
	public static final String TYPE_VBDI = "VBDI";
	public static final String TYPE_MB = "MB";
	public static final String TYPE_ACCOUNT = "ACCOUNT";
	public static final String TYPE_GT_0 = "GT0";
	
	private static AS2ValidatorService _instance;
	private static final String PROPERTIES_FILE = "/common/AS2ValidatorService.properties";
	private static final String VALIDATE = "validate";
	
    // validacije za value objekte procitane iz property file-a.
    private static Properties _validation;
    
    private AS2ValidatorService() {   
        readValidationConfiguration();
        AS2Context.setSingletonReference(this);
    }
	public static AS2ValidatorService getInstance() {
		if (_instance == null) {
			_instance = new AS2ValidatorService();
		}
		return _instance;
	}
	private void readValidationConfiguration() {
	    //Citanje pravila u novoj inacici ove clase ce biti iz SQL baze. Pavila ce se unositi 
	    //kroz Rule Editor UI. Na taj nacin ce se omoguciti promjene u vrijeme rada programa, dok kroz 
	    //property datoteke promjene mogu prihvatiti samo prije pokretanja programa 
	   _validation =  AS2Helper.readPropertyFileAsURL(AS2Context.getPropertiesPath() + PROPERTIES_FILE);
	}
	public void checkMandatory(String service, AS2Record value) {
	    //mandatory fields in prop file in format e.g. service=name,last_name
	    if(AS2Context.getInstance().MANDATORY_CHECKING_IND){
	        boolean valid = true;
	        StringBuffer msg = new StringBuffer();
		    String mandatory_fields = _validation.getProperty(service);
		    if(mandatory_fields!=null){
				StringTokenizer st = new StringTokenizer(mandatory_fields, ",");		
				String token;				
				
				while (st.hasMoreTokens()){
					token = st.nextToken();
					if(value.get(token)==null ||
					   value.getAsString(token).equals("")||
					   value.getAsString(token).equals(" ")){
					    valid=false;
					    msg.append("\n");	
					    msg.append(token);		    
					}
				}
			}
			if(!valid){
			    AS2Exception e = new AS2Exception("901");
			    e.setFieldOne(msg.toString());
			    throw e;
			}
	    }
	}

	public AS2ValidationException validate(String vo, String name, String value) {
	    StringBuffer fieldName = new StringBuffer();
	    fieldName.append(vo);
	    fieldName.append(".");	    
	    fieldName.append(name);
	    String validationType = _validation.getProperty(fieldName.toString());
	    if(validationType==null)
	        return null;
	    AS2ValidationException ve = new AS2ValidationException("901");
	    if (validate(validationType,value, ve)) {
	    	return null;
	    } else {
	    	return ve;
	    }
//	    Class param [] = new Class[]{String.class};
//	    String methodName =VALIDATE+validationType;
//	    Class targetClass = this.getClass();
//	    try{
//	        Method validatorMethod = targetClass.getMethod(methodName, param);
//	        Object ret = validatorMethod.invoke(this, new Object[]{value});
//	        if(ret instanceof Boolean){        
//	            System.out.println(ret);
//	            Boolean result = (Boolean)ret;
//	            if(!result.booleanValue())
//	            {
//	                ve = new J2EEValidationException(null);
//	            	//dodaj broj poruke u exception
//	                if (validationType.equals(TYPE_MB)) {
//	                	ve.addMessage(new J2EEMessage("VAL10000"));
//	                } else if (validationType.equals(TYPE_VBDI)) {
//	                	ve.addMessage(new J2EEMessage("VAL10001"));
//	                } else if (validationType.equals(TYPE_ACCOUNT)) {
//	                	ve.addMessage(new J2EEMessage("VAL10002"));
//	                } else if (validationType.equals(TYPE_GT_0)) {
//	                	ve.addMessage(new J2EEMessage("VAL10003"));
//	                }
//	            }
//	            
//	            
//	    	} else
//	            System.out.println("bad"+ret.getClass().getName());
//	    }catch(Exception e){
//	        J2EETrace.trace(J2EETrace.E, getClass().getName()+"validation method"+e);
//	    }
//	    return ve;
	}
	
	public boolean validate(String validationType, String value, AS2Exception ex) {
	    Class param [] = new Class[]{String.class};
	    String methodName =VALIDATE+validationType;
	    Class targetClass = this.getClass();
	    try{
	        Method validatorMethod = targetClass.getMethod(methodName, param);
	        Object ret = validatorMethod.invoke(this, new Object[]{value});
	        if(ret instanceof Boolean){        
	            
	            Boolean result = (Boolean)ret;
	            if(!result.booleanValue())
	            {
	            	//dodaj broj poruke u exception
	                if (validationType.equals(TYPE_MB)) {
	                	ex.setErrorCode("VAL10000");
	                } else if (validationType.equals(TYPE_VBDI)) {
	                	ex.setErrorCode("VAL10001");
	                } else if (validationType.equals(TYPE_ACCOUNT)) {
	                	ex.setErrorCode("VAL10002");
	                } else if (validationType.equals(TYPE_GT_0)) {
	                	ex.setErrorCode("VAL10003");
	                }
	                return false;
	            }
	            else
	            {
	            	return true;
	            }
	    	} else 
	    	{
		        AS2Trace.trace(AS2Trace.E, "bad"+ret.getClass().getName());
	    	}
	    }catch(Exception e){
	        AS2Trace.trace(AS2Trace.E, getClass().getName()+"validation method"+e);
	    }
        return false;
	}

    public static boolean validateIPAddress(String ipAddress) {
		return true;
    }
    public static boolean validateEmailAddress(String emailAddress) {
	    return true;

    }

    public static boolean validateJMBG(String jmbg){
		String numbers = "0123456789"; 
		int[] coef = new int[] {7, 6, 5, 4, 3, 2};
		int controlDigit = 0;
		int[] jmbgArray = new int[13];

		if (jmbg == null || jmbg.length() != 13)
			return false;

		for (int i = 0; i < 13; i++)
		{
			jmbgArray[i] = numbers.indexOf(jmbg.charAt(i));
			if (jmbgArray[i] == -1)
				// => nije znamenka
				return false;
		}

		for (int i = 0; i < 6; i++)
			controlDigit += coef[i] * (jmbgArray[i] + jmbgArray[i + 6]);

		controlDigit = controlDigit % 11;

		if (controlDigit != 0 && controlDigit != 1)
			controlDigit = 11 - controlDigit;

		return jmbgArray[12] == controlDigit;
    }

    public static boolean validateMB(String mb){
 	    return AS2ModuleValidator.isClientNoValid(mb);

    }
    public static boolean validateOIB(String oib){
        return validateNumber(oib);
    }
    public static boolean validateJMBGMB(String mb){
 	    boolean valid = validateJMBG(mb);
 	    if(!valid)
 	        valid = validateMB(mb);
 	    return valid;
    }
    public static boolean validatePARTIJA(String partija){
 	    return AS2ModuleValidator.isPartnoValid(partija);

    }
    public static boolean validateVBDI(String vbdi){
	    return AS2ModuleValidator.isVBDIValid(vbdi);
    }
    public static boolean validateACCOUNT(String str){
        for (int i = 0; i < str.length(); i++){  
            char chr = str.charAt(i);  
            if ( ! Character.isDigit(chr)) {  
                if(chr=='-')
                    continue;
                return false;  
            }  
        }
        return true; 
	    //return J2EEModuleValidator.isPartnoValid(account);
    }
    
    public static boolean validateGT0(String value){
    	if (validateNumber(value))
    	{
    		double val = Double.parseDouble(value);
    		if (val>0)
    			return true;
    	} 
    	return false;
    }
    public static boolean validateCreditCard(String cc){
	    return AS2CreditCardValidator.getInstance().isValid(cc);

    }
    public static boolean validateMASK(String mask){
	    return true;

    }
    public static boolean validateDDMMYYYY(String date){
	    return  AS2DateValidator.isValid(date, "dd/mm/yyyy", false) ||
	    		AS2DateValidator.isValid(date, "dd-mm-yyyy", false) ||
	    		AS2DateValidator.isValid(date, "dd.mm.yyyy", false);	    
    }
    public static boolean validateDate(String date){
        //za default date format i za lokalne datume (hr) jos vidjeti ??
	    //return J2EEDateValidator.getInstance().isValid(date, new Locale("hr"));
	    return  AS2DateValidator.isValid(date, "dd/mm/yyyy", false) ||
				AS2DateValidator.isValid(date, "dd-mm-yyyy", false) ||
				AS2DateValidator.isValid(date, "dd.mm.yyyy", false);	
    }
    public static boolean validateNumberNoSpace(String str) {  
        if (str.length()<=0)
        	return false;
        return validateNumber(str);  
     }
    public static boolean validateNumber(String str) {  
        for (int i = 0; i < str.length(); i++){  
            char chr = str.charAt(i);  
            if ( ! Character.isDigit(chr)) {  
                return false;  
            }  
        }
        return true;  
     }
    public static boolean validateNumberAndNegative(String str) {  
        for (int i = 0; i < str.length(); i++){  
            char chr = str.charAt(i);  
            if ( ! Character.isDigit(chr)) { 
                if(chr=='-'||chr=='+')
                    continue;
                return false;  
            }  
        }
        return true;  
     } 
    public static boolean validateDecimalNumber(String str) {  
        for (int i = 0; i < str.length(); i++){  
            char chr = str.charAt(i);  
            if ( ! Character.isDigit(chr)) { 
                if(chr=='.')
                    continue;
                return false;  
            }  
        }
        return true;  
     } 
    public static boolean validateAMOUNT(String str) {  
        for (int i = 0; i < str.length(); i++){  
            char chr = str.charAt(i);  
            if ( ! Character.isDigit(chr)) {  
                if(chr=='.'||chr==','||chr=='-')
                    continue;
                return false;  
            }  
        }
        return true;  
     } 
	/**
	*	Validate the card expiration date. The resulting expiration date is checked for
	*	validity. In addition to the other checks, if the date has already passed, the
	*	function returns false.
	*	
	*
	*	@param cMonth, month card expires
	*	@param cYear, year card expires. If year is less that 100, 2000 is added to the year.
	*/
	public static boolean validateCreditCardExpiration(String cMonth, String cYear){
		int iMonth, iYear;
		boolean bOK=false;
		Calendar cal;
		try {

			iMonth=Integer.parseInt(cMonth);
			iYear=Integer.parseInt(cYear);
			if (iYear < 100)
				iYear+=2000;

			bOK=((iMonth > 0) && (iMonth <= 12));
			cal=Calendar.getInstance();
			bOK&=((iYear >= cal.get(Calendar.YEAR) && (iYear < (cal.get(Calendar.YEAR)+10))));
			if ( iYear==cal.get(Calendar.YEAR) )
				bOK&=(iMonth>=(cal.get(Calendar.MONTH)+1));
		} catch ( Exception e ) {
		}
		return bOK;
	}
 
	public static boolean validatePhoneNumber(String r)	{
		String mob = r.trim();

		if (mob.length() < 6)
			return false;

		if (mob.charAt(0) == '+')
			mob = mob.substring(1);

		if (mob.startsWith("-") || mob.endsWith("-")) //$NON-NLS-1$//$NON-NLS-2$
			return false;

		char c;
		String receiver = ""; //$NON-NLS-1$

		for (int i = 0; i < mob.length(); i++)	{
			c = r.charAt(i);
			if ("0123456789".indexOf(c) >= 0) //$NON-NLS-1$
				receiver += c;
			else if (c != ' ' && c != '-')
				return false;
		}

		if (r.length() < 5)
			return false;

		return true;
	}
	public static void main(String[] args)	{
	    boolean valid = false;
	    String vo = "hr.adriacomsoftware.test.dto.securityVo";
	    String jmbg="0907967386109";
	    //valid = J2EEValidatorService.validateNumber(value);
	    valid = AS2ValidatorService.validateJMBG(jmbg);
	    //valid = J2EEValidatorService.validateDDMMYYYY("30-12-2005");
	    //valid = J2EEValidatorService.validateCreditCard("5491395006239205");
	    //J2EEValidatorService.getInstance().validate(vo, jmbg, value);	 
	    //J2EEValidatorService.getInstance().validate(vo, mb, value);
	    //J2EEValidatorService.getInstance().validate(vo, vbdi, value);
	    System.out.println(valid);
	}
}
