package com.incense.gehasajang.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @GetMapping
    public void list() {
        // TODO: 방의 전체 리스트
    }

    @GetMapping("/{roomsId}")
    public void detail() {
        // TODO: roomId 에 해당하는 room 조회
    }
}
