package com.incense.gehasajang.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    //하우스 관련
    HOUSE_NOT_FOUND("HO_001", "해당 하우스를 찾을 수 없습니다.", 404),
    NUMBER_EXCEED("HO_002", "지정된 숫자를 초과하였습니다.", 400),

    //룸 관련
    ROOM_NOT_FOUND("RO_001", "방을 찾을 수 없습니다.", 404),

    //유저 관련
    DUPLICATE("US_001", "이미 등록된 호스트입니다.", 409),
    HOST_NOT_FOUND("US_002", "호스트를 찾을 수 없습니다.", 404),
    FAIL_TO_AUTH("US_003", "인증키가 올바르지 않습니다.", 401),
    DUPLICATE_AUTH("US_004", "이미 인증이 완료된 호스트입니다.", 401),
    EXPIRATION_AUTH("US_005", "만료된 인증키입니다.", 401),
    FAIL_TO_SIGN_IN("US_006", "계정 혹은 비밀번호가 올바르지 않습니다.", 401),
    EXPIRED_TOKEN("US_007", "만료된 토큰입니다.", 401),
    INVALID_TOKEN("US_008", "유효하지 않은 토큰입니다.", 401),
    UNAUTH_MAIL("US_009", "이메일 인증이 완료되지 않았습니다.", 401),
    DELETED("US_010", "삭제된 계정입니다.", 401),
    DISABLED("US_011", "비활성화된 계정입니다.", 401),
    NON_LOGIN("US_012", "로그인을 해주세요.", 401),
    ACCESS_DENIED("US_013", "해당 리소스에 접근 권한이 없습니다.", 403),

    //공통
    INPUT_VALUE_INVALID("CO_001", "값을 확인해주세요.", 400),
    CONSTRUCTOR_VALUE_INVALID("CO_002", "값을 확인해주세요.", 400),

    //이미지 관련,
    FILE_SIZE_LIMIT_EXCEED("IMG_001", "10MB이하의 파일만 가능합니다.", 500),
    CANNOT_CONVERT_FILE("IMG_002", "MultipartFile -> File로 전환이 실패했습니다.", 500),

    //메일 관련
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
