package com.curevent.exceptions;

import org.springframework.http.HttpStatus;

public class ConflictException extends ResponceException {
    private final String message;
    private final HttpStatus httpStatus;


    public ConflictException(String message) {
        super();
        this.message = message;
        this.httpStatus = HttpStatus.NOT_FOUND;
    }
}
