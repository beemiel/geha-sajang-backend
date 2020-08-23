package com.incense.gehasajang.controller;

import com.incense.gehasajang.domain.room.Room;
import com.incense.gehasajang.dto.RoomDto;
import com.incense.gehasajang.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomDto>> list(@RequestBody List<Long> roomIds) {
        List<Room> rooms = roomService.getRooms(roomIds);
        List<RoomDto> roomDtos = new ArrayList<>();
        BeanUtils.copyProperties(rooms, roomDtos);
        return ResponseEntity.ok(roomDtos);
    }

    @GetMapping("/{roomsId}")
    public void detail(
            @PathVariable Long roomsId
    ) {
        // TODO: roomId 에 해당하는 room 조회
    }
}
