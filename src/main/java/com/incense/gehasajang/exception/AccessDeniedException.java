package com.incense.gehasajang.exception;

import com.incense.gehasajang.error.ErrorCode;

public class AccessDeniedException extends RuntimeException {
    private ErrorCode errorCode;

    public AccessDeniedException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
