package com.curevent.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class InvalidArgumentException extends ResponseException {
    private final String message;
    private final HttpStatus httpStatus;


    public InvalidArgumentException(String message) {
        this.message = message;
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }
}
