package com.incense.gehasajang.controller;


import com.github.dozermapper.core.Mapper;
import com.incense.gehasajang.domain.room.Room;
import com.incense.gehasajang.model.dto.room.RoomDto;
import com.incense.gehasajang.model.param.room.RoomCreateParam;
import com.incense.gehasajang.model.param.room.RoomDetailParam;
import com.incense.gehasajang.security.UserAuthentication;
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
import java.util.List;
import java.util.stream.Collectors;

@Validated
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

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDto> detail(
            @PathVariable Long houseId,
            @PathVariable Long roomId,
            @AuthenticationPrincipal UserAuthentication authentication
    ) {

        RoomDetailParam detailParam = RoomDetailParam.builder()
                .houseId(houseId)
                .roomId(roomId)
                .account(authentication.getAccount())
                .build();

        Room room = roomService.getRoom(detailParam);
        return ResponseEntity.ok(mapper.map(room, RoomDto.class));
    }

    @PreAuthorize("isAuthenticated() and hasAuthority('ROLE_MAIN')")
    @PostMapping
    public ResponseEntity<Void> create(
            @PathVariable @Min(1) Long houseId,
            @Valid @RequestBody RoomDto roomDto,
            @AuthenticationPrincipal UserAuthentication authentication
    ) {
        Room room = mapper.map(roomDto, Room.class);
        room.addRoomType(roomDto.getRoomTypeName());

        RoomCreateParam createParam = RoomCreateParam.builder()
                .room(room)
                .houseId(houseId)
                .account(authentication.getAccount())
                .build();

        roomService.addRoom(createParam);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
