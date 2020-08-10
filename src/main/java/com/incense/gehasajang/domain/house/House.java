package com.incense.gehasajang.domain.house;

import com.incense.gehasajang.domain.*;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class House {

    @Id @GeneratedValue
    @Column(name = "house_id")
    private Long id;

    private String uuid;

    private String name;

    @Embedded
    private Address address;

    private String main_image;

    private String main_number;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "house")
    private List<HostHouse> hostHouses;

    @OneToMany(mappedBy = "house")
    private List<HouseExtraInfo> houseExtraInfos;

    @OneToMany(mappedBy = "house")
    private List<HouseOff> houseOffs;

    @OneToMany(mappedBy = "house")
    private List<Invitation> invitations;

    @OneToMany(mappedBy = "house")
    private List<Booking> bookings;

    @OneToMany(mappedBy = "house")
    private List<Room> rooms;
}
