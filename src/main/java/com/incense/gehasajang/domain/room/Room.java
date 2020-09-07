package com.incense.gehasajang.domain.room;

import com.incense.gehasajang.domain.bed.Bed;
import com.incense.gehasajang.domain.house.House;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "room")
    private List<Bed> beds;

    @OneToMany(mappedBy = "room")
    private List<UnbookedRoom> unbookedRooms;

    @Builder
    public Room(Long id, House house, String name, RoomType roomType, String memo, int maxCapacity, int defaultCapacity, String peakAmount, String offPeakAmount, LocalDateTime deletedAt, List<Bed> beds, List<UnbookedRoom> unbookedRooms) {
        this.id = id;
        this.house = house;
        this.name = name;
        this.roomType = roomType;
        this.memo = memo;
        this.maxCapacity = maxCapacity;
        this.defaultCapacity = defaultCapacity;
        this.peakAmount = peakAmount;
        this.offPeakAmount = offPeakAmount;
        this.deletedAt = deletedAt;
        this.beds = beds;
        this.unbookedRooms = unbookedRooms;
    }

    public Long getHouseId() {
        return this.house.getId();
    }

}
