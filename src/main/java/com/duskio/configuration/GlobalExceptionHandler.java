package com.duskio.configuration;

import com.duskio.common.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleCustomExceptions(Exception ex, WebRequest request) throws Exception {
        if (ex instanceof ResourceNotFoundException subEx) {
            return handleExceptionInternal(subEx, subEx.getBody(), subEx.getHeaders(), subEx.getStatusCode(), request);
        } else {
            throw ex;
        }
    }
}
