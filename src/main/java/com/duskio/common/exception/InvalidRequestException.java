package com.duskio.common.exception;

import jakarta.annotation.Nonnull;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

public class InvalidRequestException extends RuntimeException implements ErrorResponse {

    private final ProblemDetail body;

    public InvalidRequestException(Class<?> clazz, String reason) {
        this("Invalid request for %s: '%s'".formatted(clazz.getSimpleName(), reason));
    }

    public InvalidRequestException() {
        super();
        this.body = ProblemDetail.forStatusAndDetail(getStatusCode(), "Invalid request!");
    }

    public InvalidRequestException(String message) {
        super(message);
        this.body = ProblemDetail.forStatusAndDetail(getStatusCode(), message);
    }

    public InvalidRequestException(String message, Throwable cause) {
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
