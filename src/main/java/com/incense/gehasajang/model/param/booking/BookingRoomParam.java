package com.incense.gehasajang.model.param.booking;

import com.incense.gehasajang.domain.booking.Booking;
import com.incense.gehasajang.model.dto.booking.request.BookingRoomRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class BookingRoomParam {

    private final Long houseId;
    private final Booking savedBooking;
    private final List<BookingRoomRequestDto> bookingRoomInfos;

}
