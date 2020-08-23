package com.incense.gehasajang.dto.host;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HostDto {

    private Long hostId;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 10, message = "닉네임은 2~10자까지만 가능합니다.")
    private String name;

    @Email(message = "이메일 형식을 지켜주세요.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Pattern(regexp = "^[A-Za-z0-9]{8,16}$", message = "비밀번호는 숫자+문자 조합으로 8~16자로 입력해주세요.")
    private String password;

    private String mainImage;

    private String thumbnailImage;




}
