package com.incense.gehasajang.exception;

import com.incense.gehasajang.error.ErrorCode;

public class DisabledHostException extends RuntimeException {
    private ErrorCode errorCode;

    public DisabledHostException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
