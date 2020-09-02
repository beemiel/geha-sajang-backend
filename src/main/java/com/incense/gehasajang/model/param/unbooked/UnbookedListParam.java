package com.incense.gehasajang.model.param.unbooked;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class UnbookedListParam {

    private final LocalDateTime checkIn;
    private final LocalDateTime checkOut;
    private final Long roomId;
    private final int count;

}
