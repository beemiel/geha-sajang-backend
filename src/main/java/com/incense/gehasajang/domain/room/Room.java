package com.incense.gehasajang.domain.room;

import com.incense.gehasajang.domain.UnbookedRoom;
import com.incense.gehasajang.domain.bed.Bed;
import com.incense.gehasajang.domain.house.House;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
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

    @OneToMany(mappedBy = "room")
    private List<UnbookedRoom> unbookedRooms;

}
