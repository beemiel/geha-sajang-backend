package com.incense.gehasajang.model.param.booking;

import com.incense.gehasajang.model.dto.booking.request.BookingRequestDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BookingRequestParam {

    private final BookingRequestDto request;
    private final Long houseId;
    private final String account;

    @Builder
    public BookingRequestParam(BookingRequestDto request, Long houseId, String account) {
        this.request = request;
        this.houseId = houseId;
        this.account = account;
    }
}
