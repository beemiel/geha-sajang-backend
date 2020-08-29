package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.room.Room;
import com.incense.gehasajang.domain.room.RoomRepository;
import com.incense.gehasajang.exception.NotFoundDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public List<Room> getRooms(Long houseId) {
        return roomRepository.findAllByHouse_Id(houseId);
    }

    public Room getRoom(Long houseId, Long roomId) {
        return roomRepository.findById(roomId).orElseThrow(NotFoundDataException::new);
    }
}
