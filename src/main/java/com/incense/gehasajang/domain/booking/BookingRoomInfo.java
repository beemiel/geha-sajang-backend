package com.incense.gehasajang.domain.booking;

import com.incense.gehasajang.domain.bed.Bed;
import com.incense.gehasajang.domain.room.UnbookedRoom;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    public BookingRoomInfo(Booking booking, UnbookedRoom unbookedRoom, Gender gender, Bed bed, Boolean isDownBed, Boolean isAdditionalBed) {
        this.booking = booking;
        this.unbookedRoom = unbookedRoom;
        this.gender = gender;
        this.bed = bed;
        this.isDownBed = isDownBed;
        this.isAdditionalBed = isAdditionalBed;
    }

}
