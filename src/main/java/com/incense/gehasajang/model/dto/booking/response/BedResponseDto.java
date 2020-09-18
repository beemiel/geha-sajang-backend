package com.incense.gehasajang.model.dto.booking.response;

import com.incense.gehasajang.domain.booking.Gender;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BedResponseDto {

    private String bedName;
    private Gender gender;
    private Boolean isDownBed;

}
