package com.curevent.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponceException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

    public ResponceException() {
        this.message = "Unknown error";
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ResponceException(String message){
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = message;
    }

}