package com.project.exception;

public class UserNotFoundException extends UserException {
    public UserNotFoundException(long id) {
        super("User does not exists; id = " + id);
    }
}
