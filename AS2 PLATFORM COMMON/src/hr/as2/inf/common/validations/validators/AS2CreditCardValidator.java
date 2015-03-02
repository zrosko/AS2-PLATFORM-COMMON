package hr.as2.inf.common.validations.validators;

import hr.as2.inf.common.core.AS2Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * <p>Perform credit card validations.</p>
 * <p>
 * By default, all supported card types are allowed.  You can specify which 
 * cards should pass validation by configuring the validation options.  For 
 * example,<br/><code>CreditCardValidator ccv = new CreditCardValidator(CreditCardValidator.AMEX + CreditCardValidator.VISA);</code>
 * configures the validator to only pass American Express and Visa cards.
 * If a card type is not directly supported by this class, you can implement
 * the CreditCardType interface and pass an instance into the 
 * <code>addAllowedCardType</code> method.
 * </p>
 * For a similar implementation in Perl, reference Sean M. Burke's
 * <a href="http://www.speech.cs.cmu.edu/~sburke/pub/luhn_lib.html">script</a>.
 * More information is also available
 * <a href="http://www.merriampark.com/anatomycc.htm">here</a>.
 *
 * @since Validator 1.1
 */
public class AS2CreditCardValidator {
    private static AS2CreditCardValidator _instance;
    /**
     * Option specifying that no cards are allowed.  This is useful if
     * you want only custom card types to validate so you turn off the
     * default cards with this option.
     * <br/>
     * <pre>
     * CreditCardValidator v = new CreditCardValidator(CreditCardValidator.NONE);
     * v.addAllowedCardType(customType);
     * v.isValid(aCardNumber);
     * </pre>
     * @since Validator 1.1.2
     */
    public static final int NONE = 0;

    /**
     * Option specifying that American Express cards are allowed.
     */
    public static final int AMEX = 1 << 0;

    /**
     * Option specifying that Visa cards are allowed.
     */
    public static final int VISA = 1 << 1;

    /**
     * Option specifying that Mastercard cards are allowed.
     */
    public static final int MASTERCARD = 1 << 2;

    /**
     * Option specifying that Discover cards are allowed.
     */
    public static final int DISCOVER = 1 << 3;
    
    /**
     * The CreditCardTypes that are allowed to pass validation.
     */
    private Collection cardTypes = new ArrayList();
     /**
     * Create a new CreditCardValidator with default options.
     */
    private AS2CreditCardValidator() {
        super();
        //za nove vrste kartica potrebno dodati novu inner klasu i liniju koda u ovaj konstruktor
        this.cardTypes.add(new Visa());
        this.cardTypes.add(new Amex());
        this.cardTypes.add(new Mastercard());
        this.cardTypes.add(new Discover());
        AS2Context.setSingletonReference(this);
    }
	public static AS2CreditCardValidator getInstance() {
		if (_instance == null) {
			_instance = new AS2CreditCardValidator();
		}
		return _instance;
	}
    /**
     * Checks if the field is a valid credit card number.
     * @param card The card number to validate.
     */
    public boolean isValid(String card) {
        if ((card == null) || (card.length() < 13) || (card.length() > 19)) {
            return false;
        }

        if (!this.luhnCheck(card)) {
            return false;
        }
        
        Iterator types = this.cardTypes.iterator();
        while (types.hasNext()) {
            CreditCardType type = (CreditCardType) types.next();
            if (type.matches(card)) {
                return true;
            }
        }

        return false;
    }
    
    /**
     * Add an allowed CreditCardType that participates in the card 
     * validation algorithm.
     * @param type The type that is now allowed to pass validation.
     * @since Validator 1.1.2
     */
    public void addAllowedCardType(CreditCardType type){
        this.cardTypes.add(type);
    }

    /**
     * Checks for a valid credit card number.
     * @param cardNumber Credit Card Number.
     */
    protected boolean luhnCheck(String cardNumber) {
        // number must be validated as 0..9 numeric first!!
        int digits = cardNumber.length();
        int oddOrEven = digits & 1;
        long sum = 0;
        for (int count = 0; count < digits; count++) {
            int digit = 0;
            try {
                digit = Integer.parseInt(cardNumber.charAt(count) + "");
            } catch(NumberFormatException e) {
                return false;
            }

            if (((count & 1) ^ oddOrEven) == 0) { // not
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }

        return (sum == 0) ? false : (sum % 10 == 0);
    }

    /**
     * Checks for a valid credit card number.
     * @param card Credit Card Number.
     * @deprecated This will be removed in a future release.
     */
    protected boolean isValidPrefix(String card) {

        if (card.length() < 13) {
            return false;
        }
        
        return new Visa().matches(card)
            || new Amex().matches(card)
            || new Mastercard().matches(card)
            || new Discover().matches(card);
    }
    
    /**
     * CreditCardType implementations define how validation is performed
     * for one type/brand of credit card.
     * @since Validator 1.1.2
     */
    public interface CreditCardType {
        
        /**
         * Returns true if the card number matches this type of credit
         * card.  Note that this method is <strong>not</strong> responsible
         * for analyzing the general form of the card number because 
         * <code>CreditCardValidator</code> performs those checks before 
         * calling this method.  It is generally only required to valid the
         * length and prefix of the number to determine if it's the correct 
         * type. 
         * @param card The card number, never null.
         * @return true if the number matches.
         */
        boolean matches(String card);
        
    }
    
    private class Visa implements CreditCardType {
        private static final String PREFIX = "4";
        public boolean matches(String card) {
            return (
                card.substring(0, 1).equals(PREFIX)
                    && (card.length() == 13 || card.length() == 16));
        }
    }
    
    private class Amex implements CreditCardType {
        private static final String PREFIX = "34,37,";
        public boolean matches(String card) {
            String prefix2 = card.substring(0, 2) + ",";
            return ((PREFIX.indexOf(prefix2) != -1) && (card.length() == 15));
        }
    }
    
    private class Discover implements CreditCardType {
        private static final String PREFIX = "6011";
        public boolean matches(String card) {
            return (card.substring(0, 4).equals(PREFIX) && (card.length() == 16));
        }
    }
    
    private class Mastercard implements CreditCardType {
        private static final String PREFIX = "51,52,53,54,55,";
        public boolean matches(String card) {
            String prefix2 = card.substring(0, 2) + ",";
            return ((PREFIX.indexOf(prefix2) != -1) && (card.length() == 16));
        }
    }

}
