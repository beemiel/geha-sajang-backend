package com.incense.gehasajang.domain.bed;

import com.incense.gehasajang.domain.UnbookedRoom;
import com.incense.gehasajang.domain.room.Room;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class Bed {

    @Id
    @GeneratedValue
    @Column(name = "bed_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Enumerated(EnumType.STRING)
    private BedType bedType;

    private String alias;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "bed")
    private List<UnbookedRoom> unbookedRooms;

}
