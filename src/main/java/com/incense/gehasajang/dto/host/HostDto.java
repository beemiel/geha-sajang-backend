package com.incense.gehasajang.dto.host;

import com.github.dozermapper.core.Mapping;
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

    @Mapping("id")
    private Long hostId;

    @Mapping("nickname")
    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 10, message = "닉네임은 2~10자까지만 가능합니다.")
    private String name;

    @Mapping("email")
    @Email(message = "이메일 형식을 지켜주세요.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @Mapping("password")
    @NotBlank(message = "비밀번호를 입력하세요.")
    @Pattern(regexp = "^[A-Za-z0-9]{8,16}$", message = "비밀번호는 숫자+문자 조합으로 8~16자로 입력해주세요.")
    private String password;

    @Mapping("mainImage")
    private String mainImage;

    @Mapping("thumbnailImage")
    private String thumbnailImage;

    //TODO: 2020-08-24 추후에 주소 api 적용되면 validation 추가할 것  -lynn
    @Mapping("city")
    private String city;

    @Mapping("street")
    private String street;

    @Mapping("postcode")
    private String postcode;

    @Mapping("detail")
    private String detail;

    @Mapping("isAgreeToMarketing")
    private boolean isAgreeToMarketing;

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }
}
