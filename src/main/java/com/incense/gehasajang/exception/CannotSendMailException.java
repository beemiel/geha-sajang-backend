package com.incense.gehasajang.exception;

import com.incense.gehasajang.error.ErrorCode;

public class CannotSendMailException extends RuntimeException {
    private ErrorCode errorCode;

    public CannotSendMailException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
