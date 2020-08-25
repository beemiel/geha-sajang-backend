package com.incense.gehasajang.dto.host;

import lombok.AccessLevel;
import lombok.Builder;
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
    private String nickname;

    @Email(message = "이메일 형식을 지켜주세요.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,16}$", message = "비밀번호는 숫자+문자 조합으로 8~16자로 입력해주세요.")
    private String password;

    private String profileImage;

    private String thumbnailImage;

    //TODO: 2020-08-24 추후에 주소 api 적용되면 validation 추가할 것  -lynn
    private String city;

    private String street;

    private String postcode;

    private String detail;

    private boolean isAgreeToMarketing;

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @Builder
    public HostDto(Long hostId, String nickname, String email, String password, String profileImage, String thumbnailImage, String city, String street, String postcode, String detail, boolean isAgreeToMarketing) {
        this.hostId = hostId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
        this.thumbnailImage = thumbnailImage;
        this.city = city;
        this.street = street;
        this.postcode = postcode;
        this.detail = detail;
        this.isAgreeToMarketing = isAgreeToMarketing;
    }
}
