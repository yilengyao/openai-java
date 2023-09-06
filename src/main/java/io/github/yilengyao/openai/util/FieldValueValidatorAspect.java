package io.github.yilengyao.openai.util;

import java.lang.reflect.Field;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FieldValueValidatorAspect {

    @Before("@annotation(validateFieldValue)")
    public void validateFieldValue(JoinPoint joinPoint, ValidateFieldValue validateFieldValue) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            try {
                Field field = arg.getClass().getDeclaredField(validateFieldValue.fieldName());
                field.setAccessible(true);
                Object fieldValue = field.get(arg);
                if (!validateFieldValue.expectedValue().equals(fieldValue.toString())) {
                    throw new IllegalArgumentException(validateFieldValue.errorMessage());
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Consider logging the exception rather than just printing the stack trace.
                e.printStackTrace();
            }
        }
    }
}

