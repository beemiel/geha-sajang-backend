package com.incense.gehasajang.model.dto.guest.response;

import com.github.dozermapper.core.Mapping;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestResponseDto {

    @Mapping("name")
    private String name;

    @Mapping("phoneNumber")
    private String phoneNumber;

    @Mapping("email")
    private String email;

    @Mapping("memo")
    private String memo;

}
