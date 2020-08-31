package com.incense.gehasajang.service;

import com.github.dozermapper.core.Mapper;
import com.incense.gehasajang.domain.host.HostRepository;
import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.domain.house.HouseRepository;
import com.incense.gehasajang.domain.room.Room;
import com.incense.gehasajang.domain.room.RoomRepository;
import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.exception.AccessDeniedException;
import com.incense.gehasajang.exception.NotFoundDataException;
import com.incense.gehasajang.model.param.room.RoomDetailParam;
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

    private final Mapper mapper;

    public List<Room> getRooms(Long houseId) {
        return roomRepository.findAllByHouse_Id(houseId);
    }

    public Room getRoom(RoomDetailParam detailParam) {
        authorityCheck(detailParam);
        return roomRepository.findByIdAndHouse_Id(detailParam.getRoomId(), detailParam.getHouseId())
                .orElseThrow(() -> new NotFoundDataException(ErrorCode.ROOM_NOT_FOUND));
    }

    @Transactional
    public void addRoom(Room room, Long houseId) {
        House house = houseRepository.findById(houseId).orElseThrow(() -> new NotFoundDataException(ErrorCode.HOUSE_NOT_FOUND));
        room.addHouse(house);

        roomRepository.save(room);
    }

    private void authorityCheck(RoomDetailParam detailParam) {
        hostRepository.findHouseByAccountAndHouseId(detailParam.getAccount(), detailParam.getHouseId())
                .orElseThrow(() -> new AccessDeniedException(ErrorCode.ACCESS_DENIED));
    }
}
