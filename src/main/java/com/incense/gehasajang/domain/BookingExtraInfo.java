package com.incense.gehasajang.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class BookingExtraInfo {

    @Id @GeneratedValue
    @Column(name = "booking_extra_info_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Enumerated(EnumType.STRING)
    private AttendStatus attendStatus;

    private String title;

    private String memo;

    private LocalDateTime attendDate;

    private int peopleCount;

}
