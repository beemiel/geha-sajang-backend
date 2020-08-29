package com.incense.gehasajang.controller;

import com.github.dozermapper.core.Mapper;
import com.incense.gehasajang.domain.room.Room;
import com.incense.gehasajang.model.dto.RoomDto;
import com.incense.gehasajang.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/houses/{houseId}/rooms")
public class RoomController {

    private final RoomService roomService;
    private final Mapper mapper;

    @GetMapping
    public ResponseEntity<List<RoomDto>> list(
            @PathVariable Long houseId
    ) {
        List<Room> rooms = roomService.getRooms(houseId);

        return ResponseEntity.ok(rooms.stream().map(room -> mapper.map(room, RoomDto.class)).collect(Collectors.toList()));
    }

    @GetMapping("/{roomsId}")
    public void detail(
            @PathVariable Long roomsId
    ) {
        // TODO: roomId 에 해당하는 room 조회
    }
}
