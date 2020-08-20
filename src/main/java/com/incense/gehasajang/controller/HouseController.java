package com.incense.gehasajang.controller;


import com.incense.gehasajang.domain.Address;
import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.dto.HouseDto;
import com.incense.gehasajang.service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/houses")
public class HouseController {

    private final HouseService houseService;

    @GetMapping("/{houseId}")
    public ResponseEntity<HouseDto> detail(@PathVariable Long houseId) {
        House house = houseService.getHouse(houseId);
        HouseDto houseDto = toHouseDto(house);
        return ResponseEntity.ok(houseDto);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid HouseDto houseDto) {
        House house = toHouse(houseDto);
        houseService.addHouse(house);
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
                .build();
    }

    private House toHouse(HouseDto houseDto) {
        return House.builder()
                .name(houseDto.getName())
                .address(new Address(houseDto.getCity(), houseDto.getStreet(), houseDto.getPostcode(), houseDto.getDetail()))
                .mainImage(houseDto.getMainImage())
                .thumbnailImage(houseDto.getThumbnailImage())
                .mainNumber(houseDto.getMainNumber())
                .build();
    }

}
