package com.duskio.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

public class ResourceNotFoundException extends RuntimeException implements ErrorResponse {
    
    private final ProblemDetail body;

    public ResourceNotFoundException(Class<?> clazz, String fieldName, Object fieldValue) {
        this("%s not found with %s: '%s'".formatted(clazz.getSimpleName(), fieldName, fieldValue));
    }

    public ResourceNotFoundException(Class<?> clazz, Object id) {
        this("%s not found with id: '%s'".formatted(clazz.getSimpleName(), id));
    }

    public ResourceNotFoundException(Class<?> clazz1, Class<?> clazz2, Object id) {
        this("%s not found with %s id: '%s'".formatted(clazz1.getSimpleName(), clazz2.getSimpleName(), id));
    }

    public ResourceNotFoundException(Class<?> clazz, Class<?> clazz1, Object id1, Class<?> clazz2, Object id2) {
        this("%s not found with %s id '%s' and %s id '%s'".formatted(clazz.getSimpleName(), clazz1.getSimpleName(), 
                                                                     id1, clazz2.getSimpleName(), id2));
        
    }

    public ResourceNotFoundException() {
        super();
        this.body = ProblemDetail.forStatusAndDetail(getStatusCode(), "Resource not found!");
    }

    public ResourceNotFoundException(String message) {
        super(message);
        this.body = ProblemDetail.forStatusAndDetail(getStatusCode(), message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.body = ProblemDetail.forStatusAndDetail(getStatusCode(), message);
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public ProblemDetail getBody() {
        return this.body;
    }
}
