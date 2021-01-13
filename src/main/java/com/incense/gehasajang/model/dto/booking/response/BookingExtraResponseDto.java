package com.incense.gehasajang.model.dto.booking.response;

import com.github.dozermapper.core.Mapping;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookingExtraResponseDto {

    @Mapping("id")
    private Long bookingExtraInfoId;

    @Mapping("houseExtraInfo.id")
    private Long houseExtraInfoId;

    private String extraName;
    private String date;

    @Mapping("peopleCount")
    private int count;

    @Mapping("memo")
    private String memo;

}
