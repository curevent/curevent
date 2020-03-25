package com.curevent.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class InvalidArgumentTypeException extends ResponceException {
    private final String message;
    private final HttpStatus httpStatus;


    public InvalidArgumentTypeException(String message) {
        this.message = message;
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }
}
