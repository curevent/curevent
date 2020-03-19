package com.curevent.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponceException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;

    public ResponceException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public ResponceException(String message, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
