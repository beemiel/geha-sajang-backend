package com.incense.gehasajang.domain.booking;

import com.incense.gehasajang.domain.guest.Guest;
import com.incense.gehasajang.domain.house.House;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    private Integer maleCount;

    private Integer femaleCount;

    private String requirement;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "booking")
    private List<BookingExtraInfo> bookingExtraInfos;

    @OneToMany(mappedBy = "booking")
    private List<BookingRoomInfo> bookingRoomInfos;

}
