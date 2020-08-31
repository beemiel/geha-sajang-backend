package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.booking.BookingRoomInfoRepository;
import com.incense.gehasajang.domain.room.BookedRoomRepository;
import com.incense.gehasajang.domain.room.RoomRepository;
import com.incense.gehasajang.domain.room.UnbookedRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingRoomInfoService {

    private final BookingRoomInfoRepository bookingRoomInfoRepository;
    private final UnbookedRoomRepository unbookedRoomRepository;
    private final BookedRoomRepository bookedRoomRepository;
    private final RoomRepository roomRepository;

}
