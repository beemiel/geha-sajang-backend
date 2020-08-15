package com.incense.gehasajang.controller;


import com.incense.gehasajang.service.HouseService;
import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.dto.HouseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/houses")
public class HouseController {

    private HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping("/{houseId}")
    public ResponseEntity<HouseDto> detail(@PathVariable Long houseId) {
        House house = houseService.getHouse(houseId);
        HouseDto houseDto = toHouseDto(house);
        return ResponseEntity.ok(houseDto);
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

}
