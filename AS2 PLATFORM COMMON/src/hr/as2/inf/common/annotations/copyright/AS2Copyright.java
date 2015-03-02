package hr.as2.inf.common.annotations.copyright;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface AS2Copyright {
	String value() default "Copyright (C) 2015 Adriacom Software Inc.";
	String owner() default "Adriacom Software d.o.o.";
	String createdBy() default "Zdravko Roško";
	String lastChanged() default "2014-07-01";
}
