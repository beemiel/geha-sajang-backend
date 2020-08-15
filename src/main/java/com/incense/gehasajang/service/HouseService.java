package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.HouseRepository;
import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.exception.NotFoundDataException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HouseService {

    private HouseRepository houseRepository;

    public House getHouse(Long houseId) {
        return houseRepository.findById(houseId).orElseThrow(() -> new NotFoundDataException("데이터를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
    }
}
