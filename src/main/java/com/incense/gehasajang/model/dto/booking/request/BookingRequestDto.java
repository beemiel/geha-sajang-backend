package com.incense.gehasajang.model.dto.booking.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.incense.gehasajang.model.dto.guest.request.GuestRequestDto;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookingRequestDto {

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

}
