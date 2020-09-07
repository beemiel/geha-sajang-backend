package com.incense.gehasajang.exception;

import com.incense.gehasajang.error.ErrorCode;

public class ZeroCountException extends RuntimeException {
    private ErrorCode errorCode;

    public ZeroCountException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
