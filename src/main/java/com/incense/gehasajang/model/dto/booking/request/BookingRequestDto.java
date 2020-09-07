package com.incense.gehasajang.model.dto.booking.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.dozermapper.core.Mapper;
import com.incense.gehasajang.domain.booking.Booking;
import com.incense.gehasajang.domain.guest.Guest;
import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.model.dto.guest.request.GuestRequestDto;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookingRequestDto {

    private Long bookingId;

    private GuestRequestDto guest;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime checkIn;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime checkOut;

    @Size(max = 500)
    private String requirement;

    List<BookingRoomRequestDto> bookingRooms;

    List<BookingExtraInfoRequestDto> bookingExtraInfos;

    public Booking toBooking(House house, Guest guest) {
        return Booking.builder()
                .house(house)
                .guest(guest)
                .checkIn(this.checkIn)
                .checkOut(this.checkOut)
                .femaleCount(sumFemaleCount())
                .maleCount(sumMaleCount())
                .requirement(this.requirement)
                .build();
    }

    public Guest toGuest(Mapper mapper) {
        return mapper.map(guest, Guest.class);
    }

    private int sumFemaleCount() {
        return this.bookingRooms.stream().mapToInt(BookingRoomRequestDto::getFemaleCount).sum();
    }

    private int sumMaleCount() {
        return this.bookingRooms.stream().mapToInt(BookingRoomRequestDto::getMaleCount).sum();
    }


}
