package com.incense.gehasajang.domain.room;

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

}
