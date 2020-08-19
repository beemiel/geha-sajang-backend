package com.incense.gehasajang.domain;

import com.incense.gehasajang.domain.booking.Booking;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
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
