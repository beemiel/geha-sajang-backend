package com.incense.gehasajang.dto.house;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class HouseExtraInfoDto {

    private Long HouseExtraInfoId;

    @Size(max = 25, message = "25자 이내로 입력해주세요.")
    private String title;

    @Builder
    public HouseExtraInfoDto(Long houseExtraInfoId, @Size(max = 25, message = "25자 이내로 입력해주세요.") String title) {
        HouseExtraInfoId = houseExtraInfoId;
        this.title = title;
    }
}
