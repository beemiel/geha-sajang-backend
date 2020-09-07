package com.incense.gehasajang.domain.booking;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Embeddable
public class Stay {

    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    protected Stay() {
    }

    public Stay(LocalDateTime checkIn, LocalDateTime checkOut) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public List<LocalDateTime> getDates() {
        List<LocalDateTime> dates = new ArrayList<>();
        long duration = getDuration();

        for (int i = 0; i < duration; i++) {
            dates.add(checkIn.plusDays(i));
        }
        return dates;
    }

    private long getDuration() {
        return Duration.between(checkIn, checkOut).toDays(); //시간이 모두 00 이어야 하는걸로 알고있음
    }

}
