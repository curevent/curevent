package com.curevent.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class InvalidArgumentTypeException extends ResponseException {
    private final String message;
    private final HttpStatus httpStatus;


    public InvalidArgumentTypeException(String message) {
        this.message = message;
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }
}
