package com.incense.gehasajang.domain.booking;

import com.incense.gehasajang.domain.BaseTimeEntity;
import com.incense.gehasajang.domain.guest.Guest;
import com.incense.gehasajang.domain.house.House;
import com.incense.gehasajang.util.CommonString;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.Set;

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
    private Set<BookingExtraInfo> bookingExtraInfos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "booking")
    private Set<BookingRoomInfo> bookingRoomInfos = new LinkedHashSet<>();

    @Builder
    public Booking(House house, Guest guest, Stay stay, PeopleCount peopleCount, String requirement, Set<BookingExtraInfo> bookingExtraInfos, Set<BookingRoomInfo> bookingRoomInfos) {
        this.house = house;
        this.guest = guest;
        this.stay = stay;
        this.peopleCount = peopleCount;
        this.requirement = requirement;
        this.bookingExtraInfos = bookingExtraInfos;
        this.bookingRoomInfos = bookingRoomInfos;
    }

    public int getFemaleCount() {
        return peopleCount.getFemaleCount();
    }

    public int getMaleCount() {
        return peopleCount.getMaleCount();
    }

    public String getCheckIn() {
        return stay.getCheckIn().format(DateTimeFormatter.ofPattern(CommonString.DATE_FORMAT));
    }

    public String getCheckOut() {
        return stay.getCheckOut().format(DateTimeFormatter.ofPattern(CommonString.DATE_FORMAT));
    }

}
