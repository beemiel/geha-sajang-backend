package com.incense.gehasajang.domain.booking;

import com.incense.gehasajang.domain.BaseTimeEntity;
import com.incense.gehasajang.domain.guest.Guest;
import com.incense.gehasajang.domain.house.House;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Booking extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne(fetch = LAZY)
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

    @Builder
    public Booking(Long id, House house, Guest guest, LocalDateTime checkIn, LocalDateTime checkOut, Integer maleCount, Integer femaleCount, String requirement) {
        this.id = id;
        this.house = house;
        this.guest = guest;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.maleCount = maleCount;
        this.femaleCount = femaleCount;
        this.requirement = requirement;
    }
}
