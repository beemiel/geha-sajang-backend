package com.incense.gehasajang.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotBlank;

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

    private String mainNumber;

    @Builder
    public HouseDto(Long houseId, String name, String city, String street, String postcode, String detail, String mainImage, String thumbnailImage, String mainNumber) {
        this.houseId = houseId;
        this.name = name;
        this.city = city;
        this.street = street;
        this.postcode = postcode;
        this.detail = detail;
        this.mainImage = mainImage;
        this.thumbnailImage = thumbnailImage;
        this.mainNumber = mainNumber;
    }
}
