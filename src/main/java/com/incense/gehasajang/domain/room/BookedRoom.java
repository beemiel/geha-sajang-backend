package com.incense.gehasajang.domain.room;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class BookedRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booked_room_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "unbooked_room_id")
    private UnbookedRoom unbookedRoom;

    @Builder
    public BookedRoom(Long id, UnbookedRoom unbookedRoom) {
        this.id = id;
        this.unbookedRoom = unbookedRoom;
    }
}
