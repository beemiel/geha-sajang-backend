package com.incense.gehasajang.domain.booking;

import com.incense.gehasajang.domain.UnbookedRoom;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class BookingRoomInfo {

    @Id @GeneratedValue
    @Column(name = "booking_room_info_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "unbooked_room_id")
    private UnbookedRoom unbookedRoom;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDateTime deletedAt;

}
