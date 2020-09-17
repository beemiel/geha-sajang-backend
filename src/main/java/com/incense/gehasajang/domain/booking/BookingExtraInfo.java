package com.incense.gehasajang.domain.booking;

import com.incense.gehasajang.domain.house.HouseExtraInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookingExtraInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_extra_info_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    private Boolean isAttend;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "house_extra_info_id")
    private HouseExtraInfo houseExtraInfo;

    private String memo;

    private LocalDateTime attendDate;

    private int peopleCount;

    public void addHouseExtraInfo(HouseExtraInfo houseExtraInfo) {
        this.houseExtraInfo = houseExtraInfo;
    }

    public void addBooking(Booking booking) {
        this.booking = booking;
    }

}
