package com.incense.gehasajang.model.dto.booking.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookingExtraResponseDto {

    private String extraName;
    private String date;
    private int count;
    private String memo;

}
