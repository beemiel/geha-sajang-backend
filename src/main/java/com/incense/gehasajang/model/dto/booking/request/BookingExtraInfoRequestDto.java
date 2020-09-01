package com.incense.gehasajang.model.dto.booking.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookingExtraInfoRequestDto {

    private Long bookingExtraInfoId;

    @NotNull
    private Long houseExtraInfoId;

    private Boolean isAttend;

    @Size(max = 200)
    private String memo;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime attendDate;

    private int peopleCount;

}
