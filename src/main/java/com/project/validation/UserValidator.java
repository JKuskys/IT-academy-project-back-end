package com.project.validation;

import com.project.model.User;
import com.project.model.request.UserRequest;

public class UserValidator {
    private MandatoryValueValidator mandatoryValueValidator = new MandatoryValueValidator();
    private EmailValidator emailValidator = new EmailValidator();
    private PasswordValidator passwordValidator = new PasswordValidator();

    public void validate(UserRequest user){
        mandatoryValueValidator.validate(user.getEmail(), "Elektroninis paštas");
        emailValidator.validate(user.getEmail(), "Elektroninis paštas");

        mandatoryValueValidator.validate(user.getPassword(), "Slaptažodis");
        passwordValidator.validate(user.getPassword(), "Slaptažodis");
    }
}
