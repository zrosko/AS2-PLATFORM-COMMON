package hr.as2.inf.common.validations.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
//Vaddin
public class AS2RegexpValidator extends AS2StringValidator {

	private Pattern pattern;
	private boolean complete;
	private transient Matcher matcher = null;

	/**
	 * Creates a validator for checking that the regular expression matches the
	 * complete string to validate.
	 * 
	 * @param regexp
	 *            a Java regular expression
	 * @param errorMessage
	 *            the message to display in case the value does not validate.
	 */
	public AS2RegexpValidator(String regexp, String errorMessage) {
		this(regexp, true, errorMessage);//TODO test
	}

	/**
	 * Creates a validator for checking that the regular expression matches the
	 * string to validate.
	 * 
	 * @param regexp
	 *            a Java regular expression
	 * @param complete
	 *            true to use check for a complete match, false to look for a
	 *            matching substring
	 * @param errorMessage
	 *            the message to display in case the value does not validate.
	 */
	public AS2RegexpValidator(String regexp, boolean complete, String errorMessage) {
		super(errorMessage);
		pattern = Pattern.compile(regexp);
		this.complete = complete;
	}

	@Override
	protected boolean isValidString(String value) {
		if (complete) {
			return getMatcher(value).matches();
		} else {
			return getMatcher(value).find();
		}
	}

	/**
	 * Get a new or reused matcher for the pattern
	 * 
	 * @param value
	 *            the string to find matches in
	 * @return Matcher for the string
	 */
	private Matcher getMatcher(String value) {
		if (matcher == null) {
			matcher = pattern.matcher(value);
		} else {
			matcher.reset(value);
		}
		return matcher;
	}

}