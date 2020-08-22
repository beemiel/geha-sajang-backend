package com.incense.gehasajang.domain.booking;

import com.incense.gehasajang.domain.house.HouseExtraInfo;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class BookingExtraInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_extra_info_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Enumerated(EnumType.STRING)
    private AttendStatus attendStatus;

    @ManyToOne
    @JoinColumn(name = "house_extra_info_id")
    private HouseExtraInfo houseExtraInfo;

    private String memo;

    private LocalDateTime attendDate;

    private int peopleCount;

}
