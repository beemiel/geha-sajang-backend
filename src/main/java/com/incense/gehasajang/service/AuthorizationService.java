package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthorizationService {

    private final RoomRepository roomRepository;

    public boolean checkRoom(Long roomId, Long houseId) {
        return roomRepository.existsByIdAndDeletedAtNullAndHouse_Id(roomId, houseId);
    }
}
