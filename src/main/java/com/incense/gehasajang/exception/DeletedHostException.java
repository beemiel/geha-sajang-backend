package com.incense.gehasajang.exception;

import com.incense.gehasajang.error.ErrorCode;

public class DeletedHostException extends RuntimeException {
    private ErrorCode errorCode;

    public DeletedHostException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
