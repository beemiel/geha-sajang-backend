package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.HouseRepository;
import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.exception.NotFoundDataException;
import com.incense.gehasajang.util.ErrorMessages;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HouseService {

    private HouseRepository houseRepository;

    public House getHouse(Long houseId) {
        return houseRepository.findById(houseId)
                .orElseThrow(() -> new NotFoundDataException(ErrorMessages.NOT_FOUND, HttpStatus.NOT_FOUND));
    }
}
