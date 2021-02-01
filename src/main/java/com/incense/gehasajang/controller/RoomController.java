package com.incense.gehasajang.controller;


import com.github.dozermapper.core.Mapper;
import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.domain.room.Room;
import com.incense.gehasajang.model.dto.room.RoomDto;
import com.incense.gehasajang.security.UserAuthentication;
import com.incense.gehasajang.service.HouseService;
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
    private final HouseService houseService;

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

        House house = houseService.getHouse(houseId, authentication.getAccount());

        Room room = roomService.getRoom(house.getId(), roomId);

        return ResponseEntity.ok(mapper.map(room, RoomDto.class));
    }

    @PreAuthorize("isAuthenticated() and hasAuthority('ROLE_MAIN')")
    @PostMapping
    public ResponseEntity<Void> create(
            @PathVariable @Min(1) Long houseId,
            @Valid @RequestBody List<RoomDto> roomDtos,
            @AuthenticationPrincipal UserAuthentication authentication
    ) {

        House house = houseService.getHouse(houseId, authentication.getAccount());

        List<Room> rooms = new ArrayList<>();

        for (RoomDto roomDto : roomDtos) {
            Room room = mapper.map(roomDto, Room.class);
            room.addRoomType(roomDto.getRoomTypeName());
            room.addHouse(house);
            rooms.add(room);
        }

        roomService.addRooms(rooms);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
