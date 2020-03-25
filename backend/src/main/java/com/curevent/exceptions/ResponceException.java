package com.curevent.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponceException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public ResponceException() {
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = "Unknown error";
    }

    public ResponceException(String message){
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = message;
    }

}