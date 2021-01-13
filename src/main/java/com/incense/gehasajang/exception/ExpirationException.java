package com.incense.gehasajang.exception;

import com.incense.gehasajang.error.ErrorCode;

public class ExpirationException extends RuntimeException {
    private ErrorCode errorCode;

    public ExpirationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
