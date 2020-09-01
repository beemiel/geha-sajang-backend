package com.incense.gehasajang.domain.guest;

import com.incense.gehasajang.domain.house.House;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestHouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guest_house_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "guest_id")
    private Guest guest;

}
