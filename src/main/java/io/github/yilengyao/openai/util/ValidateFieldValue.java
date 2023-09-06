package io.github.yilengyao.openai.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ValidateFieldValue {
    String fieldName();           // Name of the field to validate
    String expectedValue();       // Expected value of the field
    String errorMessage() default "Invalid field value.";   // Custom error message
}
