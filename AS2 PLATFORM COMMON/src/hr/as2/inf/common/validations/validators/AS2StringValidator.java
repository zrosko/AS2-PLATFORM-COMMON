package hr.as2.inf.common.validations.validators;

public abstract class AS2StringValidator extends AS2Validator {
	   public AS2StringValidator(String errorMessage) {
	        super(errorMessage);
	    }

	    /**
	     * Tests if the given value is a valid string.
	     * <p>
	     * Null values are always accepted. Values that are not {@link String}s are
	     * converted using {@link #toString()}. Then {@link #isValidString(String)}
	     * is used to validate the value.
	     * </p>
	     */
	    public boolean isValid(Object value) {
	        if (value == null) {
	            return true;
	        }
	        if (!(value instanceof String)) {
	            value = String.valueOf(value);
	        }
	        return isValidString((String) value);
	    }

	    /**
	     * Checks if the given string is valid.
	     * 
	     * @param value
	     *            String to check. Can never be null.
	     * @return true if the string is valid, false otherwise
	     */
	    protected abstract boolean isValidString(String value);
	}
