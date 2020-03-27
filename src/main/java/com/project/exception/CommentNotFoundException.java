package com.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CommentNotFoundException extends Exception {
    public CommentNotFoundException(long id) {
        super(String.format("Komentaras su nurodytu identifikatoriumi (%d) neegzistuoja.", id));
    }
}
