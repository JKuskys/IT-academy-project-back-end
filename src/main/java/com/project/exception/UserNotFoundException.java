package com.project.exception;

public class UserNotFoundException extends UserException {
    public UserNotFoundException(long id) {
        super("Vartotojas su nurodytu identifikatoriumi(" + id + ") neegzistuoja.");
    }
}
