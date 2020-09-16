package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.house.HouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthorizationService {

    private final HouseRepository houseRepository;

    public boolean checkRoom(Long roomId, Long houseId) {
        return houseRepository.existsByIdAndRooms_Id(houseId, roomId);
    }
}
