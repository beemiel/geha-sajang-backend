package com.incense.gehasajang.domain.bed;

import com.incense.gehasajang.domain.booking.BookingRoomInfo;
import com.incense.gehasajang.domain.room.UnbookedRoom;
import com.incense.gehasajang.domain.room.Room;
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
public class Bed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bed_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Enumerated(EnumType.STRING)
    private BedType bedType;

    private String alias;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "bed")
    private List<BookingRoomInfo> bookingRoomInfos;

    @Builder
    public Bed(Room room, BedType bedType, String alias) {
        this.room = room;
        this.bedType = bedType;
        this.alias = alias;
    }
}
