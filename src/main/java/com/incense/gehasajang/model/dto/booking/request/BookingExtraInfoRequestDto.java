package com.incense.gehasajang.model.dto.booking.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.Mapping;
import com.incense.gehasajang.domain.booking.BookingExtraInfo;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookingExtraInfoRequestDto {

    @Mapping("id")
    private Long bookingExtraInfoId;

    @NotNull
    private Long houseExtraInfoId;

    @Mapping("isAttend")
    private Boolean isAttend;

    @Size(max = 200)
    @Mapping("memo")
    private String memo;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Mapping("attendDate")
    private LocalDateTime attendDate;

    @Mapping("peopleCount")
    private int peopleCount;

    public BookingExtraInfo toBookingExtraInfos(Mapper mapper) {
        return mapper.map(this, BookingExtraInfo.class);
    }

}
