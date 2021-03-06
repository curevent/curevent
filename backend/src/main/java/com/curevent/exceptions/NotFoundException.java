package com.curevent.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class NotFoundException extends ResponseException {

    private final String message;
    private final HttpStatus httpStatus;


    public NotFoundException(String message) {
        super();
        this.message = message;
        this.httpStatus = HttpStatus.NOT_FOUND;
    }
}