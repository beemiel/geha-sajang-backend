package com.incense.gehasajang.controller;


import com.github.dozermapper.core.Mapper;
import com.incense.gehasajang.domain.room.Room;
import com.incense.gehasajang.model.dto.room.RoomDto;
import com.incense.gehasajang.security.UserAuthentication;
import com.incense.gehasajang.service.AuthorizationService;
import com.incense.gehasajang.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/houses/{houseId}/rooms")
public class RoomController {

    private final RoomService roomService;
    private final AuthorizationService authorizationService;

    private final Mapper mapper;

    @GetMapping
    public ResponseEntity<List<RoomDto>> list(
            @PathVariable Long houseId
    ) {

        List<Room> rooms = roomService.getRooms(houseId);

        return ResponseEntity.ok(rooms.stream().map(room -> mapper.map(room, RoomDto.class)).collect(Collectors.toList()));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDto> detail(
            @PathVariable Long houseId,
            @PathVariable Long roomId,
            @AuthenticationPrincipal UserAuthentication authentication
    ) {

        authorizationService.checkHouse(houseId, authentication.getAccount());

        Room room = roomService.getRoom(houseId, roomId);

        return ResponseEntity.ok(mapper.map(room, RoomDto.class));
    }

    @PreAuthorize("isAuthenticated() and hasAuthority('ROLE_MAIN')")
    @PostMapping
    public ResponseEntity<Void> create(
            @PathVariable @Min(1) Long houseId,
            @Valid @RequestBody List<RoomDto> roomDtos,
            @AuthenticationPrincipal UserAuthentication authentication
    ) {

        List<Room> rooms = new ArrayList<>();

        for (RoomDto roomDto : roomDtos) {
            Room room = mapper.map(roomDto, Room.class);
            room.addRoomType(roomDto.getRoomTypeName());
            rooms.add(room);
        }

        authorizationService.checkHouse(houseId, authentication.getAccount());

        roomService.addRooms(houseId, rooms);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
