package com.project.validation;

import com.project.exception.ValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator extends Validator<String> {

    private static final String passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{7,30}$";
    private Pattern pattern;

    public PasswordValidator() {
        pattern = Pattern.compile(passwordPattern);
    }

    @Override
    public void validate(String attribute, String message) {
        Matcher matcher = pattern.matcher(attribute.trim());

        if(!matcher.matches())
            throw new ValidationException(String.format("%s turi būti tarp 7 ir 30 simbolių, turėti bent vieną didžiąją raidę ir skaičių", message));
    }
}
