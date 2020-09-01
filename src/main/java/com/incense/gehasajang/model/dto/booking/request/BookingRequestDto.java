package com.incense.gehasajang.model.dto.booking.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.dozermapper.core.Mapper;
import com.incense.gehasajang.domain.booking.BookingExtraInfo;
import com.incense.gehasajang.domain.booking.BookingRoomInfo;
import com.incense.gehasajang.domain.guest.Guest;
import com.incense.gehasajang.model.dto.guest.request.GuestRequestDto;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public Guest toGuest(Mapper mapper) {
        return mapper.map(guest, Guest.class);
    }

    public List<BookingRoomInfo> toBookingRoomInfos(Mapper mapper) {
        return bookingRooms.stream()
                .map(bookingRoom -> mapper.map(bookingRoom, BookingRoomInfo.class))
                .collect(Collectors.toList());
    }

    public List<BookingExtraInfo> toBookingExtraInfos(Mapper mapper) {
        return bookingExtraInfos.stream()
                .map(extra -> mapper.map(extra, BookingExtraInfo.class))
                .collect(Collectors.toList());
    }

}
