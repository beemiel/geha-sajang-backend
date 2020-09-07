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

    @Embedded
    private Stay stay;

    @Embedded
    private PeopleCount peopleCount;

    private String requirement;

    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "booking")
    private List<BookingExtraInfo> bookingExtraInfos;

    @OneToMany(mappedBy = "booking")
    private List<BookingRoomInfo> bookingRoomInfos;

    @Builder
    public Booking(House house, Guest guest, Stay stay, PeopleCount peopleCount, String requirement) {
        this.house = house;
        this.guest = guest;
        this.stay = stay;
        this.peopleCount = peopleCount;
        this.requirement = requirement;
    }
}
