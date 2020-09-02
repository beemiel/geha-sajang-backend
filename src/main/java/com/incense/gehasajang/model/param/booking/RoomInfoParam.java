package com.incense.gehasajang.model.param.booking;

import com.incense.gehasajang.domain.booking.Booking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RoomInfoParam {

    private Booking booking;
    private int femaleCount;
    private int amount;

}
