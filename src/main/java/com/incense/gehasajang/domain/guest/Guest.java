package com.incense.gehasajang.domain.guest;

import com.incense.gehasajang.domain.booking.Booking;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guest_id")
    private Long id;

    private String name;

    private String phoneNumber;

    private String email;

    private String memo;

    @OneToMany(mappedBy = "guest")
    private List<Booking> bookings;

}
