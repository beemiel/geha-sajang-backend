package com.incense.gehasajang.exception;

import com.incense.gehasajang.error.ErrorCode;

public class FailToAuthenticationException extends RuntimeException {
    private ErrorCode errorCode;

    public FailToAuthenticationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {

        return errorCode;
    }
}
