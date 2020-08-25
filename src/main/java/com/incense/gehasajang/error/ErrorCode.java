package com.incense.gehasajang.error;

import lombok.Getter;

@Getter
public enum  ErrorCode {

    HOUSE_NOT_FOUND("HO_001", "해당 하우스를 찾을 수 없습니다.", 404),
    INPUT_VALUE_INVALID("???", "입력값이 올바르지 않습니다.", 400),
    NUMBER_EXCEED("HO_002", "지정된 숫자를 초과하였습니다.", 400),
    FILE_SIZE_LIMIT_EXCEED("IMG_001", "10MB이하의 파일만 가능합니다.", 500),
    CANNOT_CONVERT_FILE("IMG_002", "MultipartFile -> File로 전환이 실패했습니다.", 500),
    DUPLICATE("US_001", "이미 등록된 호스트입니다.", 409),
    HOST_NOT_FOUND("US_002", "호스트를 찾을 수 없습니다.", 404),
    FAIL_TO_AUTH("US_003", "인증키가 올바르지 않습니다.", 401),
    DUPLICATE_AUTH("US_004", "이미 인증이 완료된 호스트입니다.", 401),
    EXPIRATION_AUTH("US_005", "만료된 인증키입니다.", 401),
    CANNOT_SEND_MAIL("MA_001", "메일 전송에 실패했습니다.", 500);

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

}
