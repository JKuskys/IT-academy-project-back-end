package com.project.validation;

import com.project.exception.ValidationException;
import org.springframework.util.StringUtils;

public class MandatoryValueValidator extends Validator<String> {
    @Override
    public void validate(String attribute, String message) {
        if(StringUtils.isEmpty(attribute)){
            throw new ValidationException(String.format("%s yra privalomas", message));
        }
    }
}
