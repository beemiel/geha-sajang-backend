package com.incense.gehasajang.dto.host;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EmailCheckDto {

    @Email(message = "이메일 형식을 지켜주세요.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

}
