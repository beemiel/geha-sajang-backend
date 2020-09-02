package com.incense.gehasajang.model.param.booking;

import com.incense.gehasajang.model.dto.booking.request.BookingRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BookingRequestParam {

    private final BookingRequestDto request;
    private final Long houseId;
    private final String account;

}
