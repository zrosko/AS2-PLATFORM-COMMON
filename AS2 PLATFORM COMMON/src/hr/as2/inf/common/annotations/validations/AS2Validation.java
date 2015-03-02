package hr.as2.inf.common.annotations.validations;

import hr.as2.inf.common.annotations.copyright.AS2Author;
import hr.as2.inf.common.annotations.copyright.AS2Copyright;
import hr.as2.inf.common.annotations.copyright.AS2Version;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AS2Copyright
@AS2Author
@AS2Version
public @interface AS2Validation {
	// type of validation to do eg. birth_date=DATE, credit_card=CCARD
	String types() default "";
}