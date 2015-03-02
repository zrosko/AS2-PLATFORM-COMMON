package hr.as2.inf.common.annotations.validations;

import hr.as2.inf.common.annotations.AS2Annotation;
import hr.as2.inf.common.annotations.copyright.AS2Author;
import hr.as2.inf.common.annotations.copyright.AS2Copyright;
import hr.as2.inf.common.annotations.copyright.AS2Version;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 *	This annotation can be placed on a field, getter or setter declaration and describes how
 *	the corresponding field has to be rendered.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AS2Copyright
@AS2Author
@AS2Version
public @interface AS2ValidationField {
	boolean required() default AS2Annotation.REQUIRED;
	String match() default "";	
	int length() default  0;
	int min() default DEF_MIN;
	int max() default DEF_MAX;
	String propertyValidator() default "ALPHA_NUMERIC"; //CrediCard, Amount, Number, etc.
	public static final int DEF_MIN = Integer.MIN_VALUE;
	public static final int DEF_MAX = Integer.MAX_VALUE;
}