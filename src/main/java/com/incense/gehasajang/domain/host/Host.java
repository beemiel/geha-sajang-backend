package com.incense.gehasajang.domain.host;

import com.incense.gehasajang.domain.Address;
import com.incense.gehasajang.domain.HostHouse;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Getter
public abstract class Host {

    @Id @GeneratedValue
    @Column(name = "host_id")
    private Long id;

    private String email;

    private String nickname;

    private String password;

    private String profileImage;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "host")
    private List<HostHouse> hostHouses;

}
