package com.incense.gehasajang.service;

import com.incense.gehasajang.domain.booking.BookingRepository;
import com.incense.gehasajang.domain.house.HouseRepository;
import com.incense.gehasajang.model.param.room.BookingRequestParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final HouseRepository houseRepository;
    private final BookingRepository bookingRepository;

    private final GuestService guestService;
    private final BookingRoomInfoService bookingRoomInfoService;
    private final BookingExtraInfoService bookingExtraInfoService;

    public void addBooking(BookingRequestParam bookingRequestParam) {

    }
}
