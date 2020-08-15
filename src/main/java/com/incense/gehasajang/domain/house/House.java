package com.incense.gehasajang.domain.house;

import com.incense.gehasajang.domain.*;
import com.incense.gehasajang.domain.booking.Booking;
import com.incense.gehasajang.domain.room.Room;
import com.incense.gehasajang.domain.sms.SmsTemplate;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class House {

    @Id @GeneratedValue
    @Column(name = "house_id")
    private Long id;

    private String uuid;

    private String name;

    @Embedded
    private Address address;

    private String mainImage;
    
    private String thumbnailImage;

    private String mainNumber;

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

    @OneToMany(mappedBy = "house")
    private List<SmsTemplate> smsTemplates;

    @Builder
    public House(String name, Address address, String mainImage, String thumbnailImage, String mainNumber) {
        this.uuid = UUID.randomUUID().toString();
        this.name = name;
        this.address = address;
        this.mainImage = mainImage;
        this.thumbnailImage = thumbnailImage;
        this.mainNumber = mainNumber;
    }
}
