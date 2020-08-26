package com.incense.gehasajang.domain.host;

import com.github.dozermapper.core.Mapping;
import com.incense.gehasajang.domain.Address;
import com.incense.gehasajang.domain.HostHouse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@DiscriminatorValue("main")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MainHost extends Host {

    @Embedded
    private Address address;

    private boolean isAgreeToMarketing;

    private boolean isPassEmailAuth;

    @OneToOne(mappedBy = "host", fetch = LAZY)
    private HostAuthKey authKey;

    @Builder
    public MainHost(Long id, String email, String nickname, String password, String profileImage, String thumbnailImage, LocalDateTime deletedAt, List<HostHouse> hostHouses, Address address, boolean isAgreeToMarketing) {
        super(id, email, nickname, password, profileImage, thumbnailImage, deletedAt, hostHouses);
        this.address = address;
        this.isAgreeToMarketing = isAgreeToMarketing;
    }

    public void changeAuthPass() {
        isPassEmailAuth = true;
    }

    public String getCity() {
        return this.address.getCity();
    }

    public String getStreet() {
        return this.address.getStreet();
    }

    public String getPostcode() {
        return this.address.getPostcode();
    }

    public String getDetail() {
        return this.address.getDetail();
    }

    @PrePersist
    public void prePersist() {
        address = address == null ? new Address("","","","") : address;
    }

}
