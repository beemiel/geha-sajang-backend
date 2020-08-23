package com.incense.gehasajang.dto.house;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
public class HouseDto {

    private Long houseId;

    @NotBlank(message = "게스트 하우스 이름을 입력해주세요.")
    @Size(max = 50, message = "50이내만 가능합니다.")
    private String name;

    //TODO: 2020-08-20 추후에 주소 api 적용되면 validation 추가할 것  -lynn
    private String city;

    private String street;

    private String postcode;

    private String detail;

    private String mainImage;

    private String thumbnailImage;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "^[0-9]{9,11}$", message = "전화번호는 9~11자의 숫자만 입력해주세요.")
    private String mainNumber;

    private List<HouseExtraInfoDto> houseExtraInfoDtos;

    @Builder
    public HouseDto(Long houseId, String name, String city, String street, String postcode, String detail, String mainImage, String thumbnailImage, String mainNumber, List<HouseExtraInfoDto> houseExtraInfoDtos) {
        this.houseId = houseId;
        this.name = name;
        this.city = city;
        this.street = street;
        this.postcode = postcode;
        this.detail = detail;
        this.mainImage = mainImage;
        this.thumbnailImage = thumbnailImage;
        this.mainNumber = mainNumber;
        this.houseExtraInfoDtos = houseExtraInfoDtos;
    }
}
