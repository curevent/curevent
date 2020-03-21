package com.curevent.exceptions.handlers;

import com.curevent.exceptions.ResponceException;
import com.curevent.models.transfers.ErrorTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class ResponceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResponceException.class)
    public ResponseEntity<?> ResponceException(ResponceException e) {
        return new ResponseEntity<>(new ErrorTransfer(e.getMessage(), e.getHttpStatus().value()), e.getHttpStatus());
    }
}