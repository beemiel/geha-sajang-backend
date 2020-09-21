package com.incense.gehasajang.model.dto.booking.response;

import com.incense.gehasajang.domain.booking.Gender;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookingRoomInfoResponseDto {

    private String roomName;
    private int femaleCount;
    private int maleCount;

    public void addCount(Gender gender) {
        if(gender == Gender.FEMALE) {
            this.femaleCount++;
            return;
        }

        this.maleCount++;
    }

}
