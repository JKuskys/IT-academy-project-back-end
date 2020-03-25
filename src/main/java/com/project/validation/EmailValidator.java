package com.project.validation;

import com.project.exception.ValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator extends Validator<String> {

    private static final String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern pattern;

    public EmailValidator() {
        pattern = Pattern.compile(emailPattern);
    }

    @Override
    public void validate(String attribute, String message) {
        Matcher matcher = pattern.matcher(attribute.trim());

        if(!matcher.matches())
            throw new ValidationException(String.format("%s yra netinkamas", message));
    }
}
