package com.project.validation;

import com.project.model.User;

public class UserValidator {
    private MandatoryValueValidator mandatoryValueValidator = new MandatoryValueValidator();

    public void validate(User user){
        mandatoryValueValidator.validate(user.getEmail(), "Email");
        mandatoryValueValidator.validate(user.getPassword(), "Password");
    }
}
