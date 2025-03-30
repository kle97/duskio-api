package com.duskio.common.exception;

import jakarta.annotation.Nonnull;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

public class DuplicateResourceException extends RuntimeException implements ErrorResponse {

    private final ProblemDetail body;

    public DuplicateResourceException(Class<?> clazz, String fieldName, Object fieldValue) {
        this("%s already exists with %s: '%s'".formatted(clazz.getSimpleName(), fieldName, fieldValue));
    }

    public DuplicateResourceException(Class<?> clazz, Object id) {
        this("%s already exists with id: '%s'".formatted(clazz.getSimpleName(), id));
    }

    public DuplicateResourceException(Class<?> clazz1, Class<?> clazz2, Object id) {
        this("%s already exists with %s id: '%s'".formatted(clazz1.getSimpleName(), clazz2.getSimpleName(), id));
    }

    public DuplicateResourceException(Class<?> clazz, Class<?> clazz1, Object id1, Class<?> clazz2, Object id2) {
        this("%s already exists with %s id '%s' and %s id '%s'".formatted(clazz.getSimpleName(), clazz1.getSimpleName(),
                                                                          id1, clazz2.getSimpleName(), id2));

    }

    public DuplicateResourceException() {
        super();
        this.body = ProblemDetail.forStatusAndDetail(getStatusCode(), "Resource already exists!");
    }

    public DuplicateResourceException(String message) {
        super(message);
        this.body = ProblemDetail.forStatusAndDetail(getStatusCode(), message);
    }

    public DuplicateResourceException(String message, Throwable cause) {
        super(message, cause);
        this.body = ProblemDetail.forStatusAndDetail(getStatusCode(), message);
    }
    
    @Nonnull
    @Override
    public HttpStatusCode getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }

    @Nonnull
    @Override
    public ProblemDetail getBody() {
        return this.body;
    }
}
