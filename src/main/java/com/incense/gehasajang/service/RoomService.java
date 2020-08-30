package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.host.HostRepository;
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

    public List<Room> getRooms(Long houseId) {
        return roomRepository.findAllByHouse_Id(houseId);
    }

    public Room getRoom(RoomDetailParam detailParam) {
        authorityCheck(detailParam);
        return roomRepository.findByIdAndHouse_Id(detailParam.getRoomId(), detailParam.getHouseId())
                .orElseThrow(() -> new NotFoundDataException(ErrorCode.ROOM_NOT_FOUND));
    }

    private void authorityCheck(RoomDetailParam detailParam) {
        hostRepository.findHouseByAccountAndHouseId(detailParam.getAccount(), detailParam.getHouseId())
                .orElseThrow(() -> new AccessDeniedException(ErrorCode.ACCESS_DENIED));
    }
}
