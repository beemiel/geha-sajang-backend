package com.incense.gehasajang.domain.booking;

import com.incense.gehasajang.domain.room.UnbookedRoom;
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

    private LocalDateTime deletedAt;

}
