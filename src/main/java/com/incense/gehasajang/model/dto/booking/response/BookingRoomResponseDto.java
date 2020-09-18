package com.incense.gehasajang.model.dto.booking.response;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookingRoomResponseDto {

    private String roomName;
    private int femaleCount;
    private int maleCount;
    private List<BedResponseDto> bedResponseDtos;

}
