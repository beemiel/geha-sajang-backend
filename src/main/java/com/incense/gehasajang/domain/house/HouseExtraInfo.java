package com.incense.gehasajang.domain.house;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class HouseExtraInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_extra_info_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    private String title;

    private LocalDateTime deletedAt;

}
