package com.incense.gehasajang.model.dto.booking.response;

import com.github.dozermapper.core.Mapping;
import com.incense.gehasajang.model.dto.guest.response.GuestResponseDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookingResponseDto {

    @Mapping("id")
    private Long id;

    private int femaleCount;
    private int maleCount;
    private String checkIn;
    private String checkOut;

    @Mapping("requirement")
    private String requirement;

    private GuestResponseDto guestResponseDto;
    private List<BookingRoomInfoResponseDto> bookingRoomInfoResponseDto = new ArrayList<>();
    private List<BookingExtraResponseDto> bookingExtraResponseDto = new ArrayList<>();

    public void addGuest(GuestResponseDto guestResponseDto) {
        this.guestResponseDto = guestResponseDto;
    }

    public void addBookingRoomInfoResponseDto(List<BookingRoomInfoResponseDto> bookingRoomInfoResponseDto) {
        this.bookingRoomInfoResponseDto = bookingRoomInfoResponseDto;
    }

    public void addBookingExtraResponseDto(List<BookingExtraResponseDto> bookingExtraResponseDto) {
        this.bookingExtraResponseDto = bookingExtraResponseDto;
    }
}
