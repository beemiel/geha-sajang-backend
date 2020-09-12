package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.host.HostRepository;
import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.domain.house.HouseRepository;
import com.incense.gehasajang.domain.room.Room;
import com.incense.gehasajang.domain.room.RoomRepository;
import com.incense.gehasajang.error.ErrorCode;
import com.incense.gehasajang.exception.AccessDeniedException;
import com.incense.gehasajang.exception.NotFoundDataException;
import com.incense.gehasajang.model.param.room.RoomCreateParam;
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

    private String account;
    private Long houseId;

    public List<Room> getRooms(Long houseId) {
        return roomRepository.findAllByHouse_Id(houseId);
    }

    public Room getRoom(RoomDetailParam detailParam) {
        account = detailParam.getAccount();
        houseId = detailParam.getHouseId();

        authorityCheck(account, houseId);

        return roomRepository.findByIdAndHouse_Id(detailParam.getRoomId(), houseId)
                .orElseThrow(() -> new NotFoundDataException(ErrorCode.ROOM_NOT_FOUND));
    }

    @Transactional
    public void addRooms(RoomCreateParam createParam) {
        account = createParam.getAccount();
        houseId = createParam.getHouseId();

        authorityCheck(account, houseId);

        House house = houseRepository.findById(createParam.getHouseId()).orElseThrow(() -> new NotFoundDataException(ErrorCode.HOUSE_NOT_FOUND));

        List<Room> rooms = createParam.getRooms();
        for (Room room : rooms) {
            room.addHouse(house);
        }

        roomRepository.saveAll(rooms);
    }

    private void authorityCheck(String account, Long houseId) {
        hostRepository.findByAccountAndHostHouses_House_Id(account, houseId)
                .orElseThrow(() -> new AccessDeniedException(ErrorCode.ACCESS_DENIED));
    }
}
