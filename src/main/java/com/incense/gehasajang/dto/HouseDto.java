package com.incense.gehasajang.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class HouseDto {

    private Long houseId;

    private String name;

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
