package com.incense.gehasajang.exception;

import com.incense.gehasajang.error.ErrorCode;

public class CannotConvertException extends RuntimeException {
    private ErrorCode errorCode;

    public CannotConvertException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
