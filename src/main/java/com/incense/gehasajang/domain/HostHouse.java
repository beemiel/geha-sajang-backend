package com.incense.gehasajang.domain;

import com.incense.gehasajang.domain.host.Host;
import com.incense.gehasajang.domain.house.House;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class HostHouse {

    @Id @GeneratedValue
    @Column(name = "host_house_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "host_id")
    private Host host;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

}
