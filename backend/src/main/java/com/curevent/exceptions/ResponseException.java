package com.curevent.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ResponseException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public ResponseException() {
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = "Unknown error";
    }

    public ResponseException(String message){
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = message;
    }

}