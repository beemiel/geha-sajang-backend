package com.incense.gehasajang.controller;


import com.incense.gehasajang.domain.Address;
import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.domain.house.HouseExtraInfo;
import com.incense.gehasajang.dto.house.HouseDto;
import com.incense.gehasajang.dto.house.HouseExtraInfoDto;
import com.incense.gehasajang.service.HouseService;
import com.incense.gehasajang.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/houses")
public class HouseController {

    private final HouseService houseService;

    private final S3Service s3Service;

    @GetMapping("/{houseId}")
    public ResponseEntity<HouseDto> detail(@PathVariable Long houseId) {
        House house = houseService.getHouse(houseId);
        HouseDto houseDto = toHouseDto(house);
        return ResponseEntity.ok(houseDto);
    }

    @PostMapping()
    public ResponseEntity<Void> create(@Valid HouseDto houseDto, MultipartFile file, String extra) throws IOException {
        String imgPath = s3Service.upload(file, "house");
        House house = toHouse(houseDto, imgPath);
        houseService.addHouse(house, extra);
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
                .address(new Address(houseDto.getCity(), houseDto.getStreet(), houseDto.getPostcode(), houseDto.getDetail()))
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

}
