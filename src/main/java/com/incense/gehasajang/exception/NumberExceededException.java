package com.incense.gehasajang.exception;

import com.incense.gehasajang.error.ErrorCode;

public class NumberExceededException extends RuntimeException {
    private ErrorCode errorCode;

    public NumberExceededException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
