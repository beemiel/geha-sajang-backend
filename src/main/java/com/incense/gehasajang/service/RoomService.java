package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.host.HostRepository;
import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.domain.house.HouseRepository;
import com.incense.gehasajang.domain.room.Room;
import com.incense.gehasajang.domain.room.RoomRepository;
import com.incense.gehasajang.error.ErrorCode;
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
    private final HostRepository hostRepository;
    private final HouseRepository houseRepository;

    public List<Room> getRooms(Long houseId) {
        return roomRepository.findAllByHouse_Id(houseId);
    }

    public Room getRoom(Long houseId, Long roomId) {

        return roomRepository.findByIdAndHouse_Id(roomId, houseId)
                .orElseThrow(() -> new NotFoundDataException(ErrorCode.ROOM_NOT_FOUND));
    }

    @Transactional
    public void addRooms(Long houseId, List<Room> rooms) {

        House house = houseRepository.findById(houseId).orElseThrow(() -> new NotFoundDataException(ErrorCode.HOUSE_NOT_FOUND));

        for (Room room : rooms) {
            room.addHouse(house);
        }

        roomRepository.saveAll(rooms);
    }

}
