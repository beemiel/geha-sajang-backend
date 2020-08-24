package com.incense.gehasajang.domain.house;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class HouseOff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_off_id")
    private Long id;

    private LocalDateTime offDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "house_id")
    private House house;

}
