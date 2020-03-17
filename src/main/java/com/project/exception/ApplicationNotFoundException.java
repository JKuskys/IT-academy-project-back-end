package com.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ApplicationNotFoundException extends ApplicationException {
    public ApplicationNotFoundException(long id) {
        super("Aplikacija su nurodytu identifikatoriumi (" + id + ") neegzistuoja.");
    }
}
