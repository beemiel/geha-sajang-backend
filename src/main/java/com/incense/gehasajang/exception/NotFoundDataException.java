package com.incense.gehasajang.exception;

import org.springframework.http.HttpStatus;

public class NotFoundDataException extends RuntimeException {

    private HttpStatus httpStatus;

    public NotFoundDataException(String message, HttpStatus status) {
        super(message);
        this.httpStatus = status;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
