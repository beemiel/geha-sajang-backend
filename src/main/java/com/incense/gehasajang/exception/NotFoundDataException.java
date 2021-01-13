package com.incense.gehasajang.exception;

import com.incense.gehasajang.error.ErrorCode;

public class NotFoundDataException extends RuntimeException {
    private ErrorCode errorCode;

    public NotFoundDataException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
