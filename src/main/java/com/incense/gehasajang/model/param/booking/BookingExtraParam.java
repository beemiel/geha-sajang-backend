package com.incense.gehasajang.model.param.booking;

import com.incense.gehasajang.domain.booking.Booking;
import com.incense.gehasajang.model.dto.booking.request.BookingExtraInfoRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class BookingExtraParam {

    private Long houseId;
    private Booking savedBooking;
    List<BookingExtraInfoRequestDto> bookingExtraInfos;

}
