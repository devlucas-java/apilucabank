package com.github.devlucasjava.apilucabank.utils;

import com.github.devlucasjava.apilucabank.utils.annotation.ValidAge;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class AgeValidator implements ConstraintValidator<ValidAge, LocalDate> {

    private static final int MIN_AGE = 18;

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        if (birthDate == null) {
            return true;
        }

        int age = Period.between(birthDate, LocalDate.now()).getYears();
        return age >= MIN_AGE;
    }
}