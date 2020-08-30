package com.incense.gehasajang.exception;

import com.incense.gehasajang.error.ErrorCode;

public class DuplicateHostException extends RuntimeException {
    private ErrorCode errorCode;

    public DuplicateHostException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
