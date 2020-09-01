package com.incense.gehasajang.domain.guest;

import com.incense.gehasajang.domain.booking.Booking;
import com.incense.gehasajang.model.dto.guest.request.GuestRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
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

    @OneToMany(mappedBy = "guest")
    private List<GuestHouse> guestHouses;

    @Builder
    public Guest(Long id, String name, String phoneNumber, String email, String memo) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.memo = memo;
    }
    
}
