package hr.as2.inf.common.validations.validators;
/**
 * String validator for e-mail addresses. The e-mail address syntax is not
 * complete according to RFC 822 but handles the vast majority of valid e-mail
 * addresses correctly.
 */
public class AS2EmailValidator extends AS2RegexpValidator {
    /**
     * Creates a validator for checking that a string is a syntactically valid
     * e-mail address.
     * 
     * @param errorMessage
     *            the message to display in case the value does not validate.
     */
    public AS2EmailValidator(String errorMessage) {
        super("^([a-zA-Z0-9_\\.\\-+])+@(([a-zA-Z0-9-])+\\.)+([a-zA-Z0-9]{2,4})+$",true, errorMessage);
    }
}
