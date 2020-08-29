package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.host.HostRepository;
import com.incense.gehasajang.domain.room.Room;
import com.incense.gehasajang.domain.room.RoomRepository;
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
        return roomRepository.findById(detailParam.getRoomId())
                .orElseThrow(NotFoundDataException::new);
    }

    private void authorityCheck(RoomDetailParam detailParam) {
        hostRepository.findHouseByAccountAndHouseId(detailParam.getAccount(), detailParam.getHouseId())
                .orElseThrow(AccessDeniedException::new);
    }
}
