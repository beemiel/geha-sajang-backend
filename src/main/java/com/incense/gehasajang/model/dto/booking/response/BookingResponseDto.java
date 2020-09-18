package com.incense.gehasajang.model.dto.booking.response;

import com.incense.gehasajang.model.dto.guest.response.GuestResponseDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookingResponseDto {

    private Long id;
    private int femaleCount;
    private int maleCount;
    private String checkIn;
    private String checkOut;
    private String requirement;
    private GuestResponseDto guestResponseDto;
    private List<BookingRoomResponseDto> bookingRoomResponseDto;
    private List<BookingExtraResponseDto> bookingExtraResponseDto;

}
