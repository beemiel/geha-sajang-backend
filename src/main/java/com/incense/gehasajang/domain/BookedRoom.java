package com.incense.gehasajang.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class BookedRoom {

    @Id @GeneratedValue
    @Column(name = "booked_room_id")
    private Long id;

    @OneToOne(mappedBy = "bookedRoom")
    private UnbookedRoom unbookedRoom;

}
