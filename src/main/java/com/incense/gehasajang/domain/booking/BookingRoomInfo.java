package com.incense.gehasajang.domain.booking;

import com.incense.gehasajang.domain.room.UnbookedRoom;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class BookingRoomInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_room_info_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "unbooked_room_id")
    private UnbookedRoom unbookedRoom;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDateTime deletedAt;

    @Builder
    public BookingRoomInfo(Booking booking, UnbookedRoom unbookedRoom, Gender gender) {
        this.booking = booking;
        this.unbookedRoom = unbookedRoom;
        this.gender = gender;
    }
}
