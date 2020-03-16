package com.curevent.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class TagExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<ErrorResponse> userNotFoundHandle(Exception ex, WebRequest request) {

        ErrorResponse errors = new ErrorResponse();
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);

    }

    @Getter
    @Setter
    private static class ErrorResponse {
        private int status;
        private String error;
    }
}
