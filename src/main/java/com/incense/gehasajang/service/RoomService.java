package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.RoomRepository;
import com.incense.gehasajang.domain.house.HouseRepository;
import com.incense.gehasajang.domain.room.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final HouseRepository houseRepository;

    public List<Room> getRooms(Long houseId) {
        return roomRepository.findByRoom_House_Id(houseId);
    }
}
