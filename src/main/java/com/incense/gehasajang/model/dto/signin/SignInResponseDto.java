package com.incense.gehasajang.model.dto.signin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponseDto {

    private String accessToken;
    private String registerState;
    private String nickname;
    //TODO: 2020-09-09 썸네일 이미지 추가되면 수정해야함 -lynn
    private String profileImage;

}
