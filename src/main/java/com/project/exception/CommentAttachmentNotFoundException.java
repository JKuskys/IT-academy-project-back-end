package com.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CommentAttachmentNotFoundException extends Exception {
    public CommentAttachmentNotFoundException(long id) {
        super(String.format("Komentaras su nurodytu identifikatoriumi (%d) neturi prisegto failo.", id));
    }
}
