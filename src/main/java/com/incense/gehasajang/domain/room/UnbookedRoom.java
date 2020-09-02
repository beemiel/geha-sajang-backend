package com.incense.gehasajang.domain.room;

import com.incense.gehasajang.domain.bed.Bed;
import com.incense.gehasajang.domain.booking.BookingRoomInfo;
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
public class UnbookedRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unbooked_room_id")
    private Long id;

    private LocalDateTime entryDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "bed_id")
    private Bed bed;

    private Boolean isDownBed;

    private Boolean isAdditionalBed;

    private String todayAmount;

    @OneToOne(mappedBy = "unbookedRoom", fetch = LAZY)
    private BookingRoomInfo bookingRoomInfos;

    @OneToOne(mappedBy = "unbookedRoom", fetch = LAZY)
    private BookedRoom bookedRoom;

    @Builder
    public UnbookedRoom(Long id, LocalDateTime entryDate, Room room, String todayAmount, BookedRoom bookedRoom) {
        this.id = id;
        this.entryDate = entryDate;
        this.room = room;
        this.todayAmount = todayAmount;
        this.bookedRoom = bookedRoom;
    }
}
