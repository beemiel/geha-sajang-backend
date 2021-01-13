package com.incense.gehasajang.exception;

import com.incense.gehasajang.error.ErrorCode;

public class UnAuthMailException extends RuntimeException {
    private ErrorCode errorCode;

    public UnAuthMailException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
