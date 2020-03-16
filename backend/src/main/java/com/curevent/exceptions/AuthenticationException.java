package com.curevent.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class AuthenticationException extends RuntimeException {

    private HttpStatus httpStatus;
    private String message;

    public AuthenticationException(String message, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
