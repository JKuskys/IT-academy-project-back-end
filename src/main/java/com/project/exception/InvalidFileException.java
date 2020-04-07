package com.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidFileException extends Exception {
    public InvalidFileException(String format) {
        super(String.format("Failas yra netinkamo formato (%s).", format));
    }
}
