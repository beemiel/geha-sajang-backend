package com.incense.gehasajang.error;

import lombok.Getter;

@Getter
public enum  ErrorCode {

    HOUSE_NOT_FOUND("HO_001", "해당 하우스를 찾을 수 없습니다.", 404),
    INPUT_VALUE_INVALID("???", "입력값이 올바르지 않습니다.", 400);

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

}
