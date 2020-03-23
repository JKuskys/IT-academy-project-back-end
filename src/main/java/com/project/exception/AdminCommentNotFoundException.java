package com.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AdminCommentNotFoundException extends Exception {
    public AdminCommentNotFoundException(long id) {
        super("Komentaras su nurodytu identifikatoriumi (" + id + ") neegzistuoja.");
    }
}
