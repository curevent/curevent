package com.curevent.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponceException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;

    public ResponceException() {
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = "Unknown error";
    }
}
