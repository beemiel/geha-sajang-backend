package com.incense.gehasajang.model.dto.guest.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestResponseDto {

    private String name;
    private String number;
    private String email;
    private String memo;

}
