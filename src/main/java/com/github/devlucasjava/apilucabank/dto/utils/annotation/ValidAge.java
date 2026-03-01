package com.github.devlucasjava.apilucabank.dto.utils.annotation;

import com.github.devlucasjava.apilucabank.dto.utils.AgeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeValidator.class)
@Documented
public @interface ValidAge {
    String message() default "Age must be greater than 18 years";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}