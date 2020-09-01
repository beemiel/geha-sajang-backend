package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.room.BookedRoomRepository;
import com.incense.gehasajang.domain.room.RoomRepository;
import com.incense.gehasajang.domain.room.UnbookedRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UnbookedRoomService {

    private final UnbookedRoomRepository unbookedRoomRepository;
    private final BookedRoomRepository bookedRoomRepository;
    private final RoomRepository roomRepository;

}
