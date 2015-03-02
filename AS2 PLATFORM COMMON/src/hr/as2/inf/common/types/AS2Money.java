package hr.as2.inf.common.types;

import hr.as2.inf.common.format.AS2FormatterService;
import hr.as2.inf.common.validations.AS2ValidatorService;

import java.math.BigDecimal;

public class AS2Money {
    
    public static String prepareFormating(String obj) {
        if(obj==null || obj.equals(""))
            obj = "0";
        boolean valid = AS2ValidatorService.validateAMOUNT(obj.toString());
   
        if(valid){
            BigDecimal amount = new BigDecimal(obj.toString().replace(',','.'));
            return AS2FormatterService.formatAmountForDisplayBigDecimal((amount));
        }
        return obj.toString();
    }
    public static String removeFormating(String str) throws Exception {
        boolean valid = AS2ValidatorService.validateAMOUNT(str);
        StringBuffer sb = new StringBuffer();
        
        if(valid){
            
            for (int i = 0; i < str.length(); i++){  
                char chr = str.charAt(i);  
                if ( ! Character.isDigit(chr)) {  
                    if(chr==',')
                        sb.append('.');
                    if(chr=='.')
                        continue;
                }else{
                    sb.append(chr);
                }
            }
            return sb.toString();
        }
        return str;
    }
}