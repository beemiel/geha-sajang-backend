package com.incense.gehasajang.controller;


import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.domain.house.HouseExtraInfo;
import com.incense.gehasajang.error.ErrorResponse;
import com.incense.gehasajang.exception.NumberExceededException;
import com.incense.gehasajang.model.dto.house.HouseDto;
import com.incense.gehasajang.model.dto.house.HouseExtraInfoDto;
import com.incense.gehasajang.security.UserAuthentication;
import com.incense.gehasajang.service.AuthorizationService;
import com.incense.gehasajang.service.HouseService;
import com.incense.gehasajang.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/houses")
public class HouseController {

    private final HouseService houseService;
    private final S3Service s3Service;

    @GetMapping("/{houseId}")
    public ResponseEntity<HouseDto> detail(
            @PathVariable @Min(value = 1) Long houseId,
            @AuthenticationPrincipal UserAuthentication authentication
    ) {
        House house = houseService.getHouse(houseId, authentication.getAccount());
        //TODO: 2020-09-04 매퍼로 바꿀 것 -lynn
        HouseDto houseDto = toHouseDto(house);
        return ResponseEntity.ok(houseDto);
    }

    @PostMapping()
    @PreAuthorize("isAuthenticated() and hasAuthority('ROLE_MAIN')")
    public ResponseEntity<Void> create(
            @Valid HouseDto houseDto,
            MultipartFile image,
            String extra,
            @AuthenticationPrincipal UserAuthentication authentication
    ) throws IOException {
        String imgPath = s3Service.upload(image, "house");
        House house = toHouse(houseDto, imgPath);
        houseService.addHouse(house, extra, authentication.getAccount());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private HouseDto toHouseDto(House house) {
        return HouseDto.builder()
                .houseId(house.getId())
                .name(house.getName())
                .city(house.getAddress().getCity())
                .street(house.getAddress().getStreet())
                .postcode(house.getAddress().getPostcode())
                .detail(house.getAddress().getDetail())
                .mainImage(house.getMainImage())
                .thumbnailImage(house.getThumbnailImage())
                .mainNumber(house.getMainNumber())
                .houseExtraInfoDtos(toHouseExtraInfoDtos(house.getHouseExtraInfos()))
                .build();
    }

    private House toHouse(HouseDto houseDto, String imgPath) {
        return House.builder()
                .name(houseDto.getName())
                .mainImage(imgPath)
                .thumbnailImage(houseDto.getThumbnailImage())
                .mainNumber(houseDto.getMainNumber())
                .build();
    }

    private List<HouseExtraInfoDto> toHouseExtraInfoDtos(List<HouseExtraInfo> houseExtraInfos) {
        return houseExtraInfos.stream()
                .map(houseExtraInfo ->
                        HouseExtraInfoDto.builder()
                                .houseExtraInfoId(houseExtraInfo.getId())
                                .title(houseExtraInfo.getTitle())
                                .build())
                .collect(Collectors.toList());
    }

    /**
     * 하우스 추가 정보가 최대 개수를 초과
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberExceededException.class)
    public ErrorResponse handleNumberExceededException(NumberExceededException e) {
        return ErrorResponse.buildError(e.getErrorCode());
    }

}
