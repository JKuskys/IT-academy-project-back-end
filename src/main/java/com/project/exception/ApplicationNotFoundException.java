package com.project.exception;

public class ApplicationNotFoundException extends ApplicationException {
    public ApplicationNotFoundException(long id) {
        super("Entry does not exists; id = " + id);
    }
}
