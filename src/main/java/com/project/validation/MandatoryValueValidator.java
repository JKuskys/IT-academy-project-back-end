package com.project.validation;

import com.project.exception.ValidationException;

public class MandatoryValueValidator extends Validator<String> {
    @Override
    public void validate(String attribute, String message) {
        if(attribute == null || attribute.trim().isEmpty())
            throw new ValidationException(message +" is mandatory");
    }
}
