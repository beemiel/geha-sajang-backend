package com.incense.gehasajang.domain;

import com.incense.gehasajang.domain.house.House;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class Room {

    @Id @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    private String name;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    private String memo;

    private int maxCapacity;

    private int defaultCapacity;

    private String peakAmount;

    private String offPeakAmount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "room")
    private List<Bed> beds;

}
