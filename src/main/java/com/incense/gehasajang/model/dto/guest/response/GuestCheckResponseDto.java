package com.incense.gehasajang.model.dto.guest.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class GuestCheckResponseDto {

    private final Long guestId;
    private final String name;
    private final String phoneNumber;
    private final String email;
    private final String memo;
    private LocalDateTime lastBooking;

}
