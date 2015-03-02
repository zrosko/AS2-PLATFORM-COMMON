package hr.as2.inf.common.annotations.copyright;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.TYPE}) 
@Retention(RetentionPolicy.RUNTIME)
public @interface AS2Version {
	 double number() default 2.0;
}