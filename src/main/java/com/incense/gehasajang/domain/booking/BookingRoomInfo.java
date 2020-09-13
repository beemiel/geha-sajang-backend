package com.incense.gehasajang.domain.booking;

import com.incense.gehasajang.domain.bed.Bed;
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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "unbooked_room_id")
    private UnbookedRoom unbookedRoom;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "bed_id")
    private Bed bed;

    private Boolean isDownBed;

    private Boolean isAdditionalBed;

    private LocalDateTime deletedAt;

    @Builder
    public BookingRoomInfo(Booking booking, UnbookedRoom unbookedRoom, Gender gender) {
        this.booking = booking;
        this.unbookedRoom = unbookedRoom;
        this.gender = gender;
    }
}
