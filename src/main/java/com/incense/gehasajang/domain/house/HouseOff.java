package com.incense.gehasajang.domain.house;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class HouseOff {

    @Id
    @GeneratedValue
    @Column(name = "house_off_id")
    private Long id;

    private LocalDateTime offDate;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

}
