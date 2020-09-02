package com.incense.gehasajang.model.param.booking;

import com.incense.gehasajang.domain.booking.Booking;
import com.incense.gehasajang.model.dto.booking.request.BookingRoomRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class BookingRoomParam {

    private final LocalDateTime checkIn;
    private final LocalDateTime checkOut;
    private final Booking booking;
    private final List<BookingRoomRequestDto> bookingRoomInfos;

}
