package com.example.global.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        String regexPattern = "^010-\\d{4}-\\d{4}";
        if(!s.matches(regexPattern)) {
            return false;
        }
        return true;
    }
}
