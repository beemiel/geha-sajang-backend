package com.incense.gehasajang.exception;

import com.incense.gehasajang.error.ErrorCode;

public class DuplicateAuthException extends RuntimeException {
    private ErrorCode errorCode;

    public DuplicateAuthException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
