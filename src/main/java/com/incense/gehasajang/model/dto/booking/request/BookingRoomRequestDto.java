package com.incense.gehasajang.model.dto.booking.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookingRoomRequestDto {

    @NotNull
    private Long roomId;

    private int maleCount;

    private int femaleCount;

    public int sum() {
        return femaleCount + maleCount;
    }

}
