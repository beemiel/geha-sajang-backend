package com.incense.gehasajang.domain.host;

import com.incense.gehasajang.domain.Address;
import com.incense.gehasajang.domain.house.HostHouse;
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
    public MainHost(Long id, String account, String nickname, String password, String type, String profileImage, String thumbnailImage, boolean isActive, LocalDateTime deletedAt, List<HostHouse> hostHouses, Address address, boolean isAgreeToMarketing, boolean isPassEmailAuth, HostAuthKey authKey) {
        super(id, account, nickname, password, type, profileImage, thumbnailImage, isActive, deletedAt, hostHouses);
        this.address = address;
        this.isAgreeToMarketing = isAgreeToMarketing;
    }

    @PrePersist
    public void prePersist() {
        address = address == null ? new Address("", "", "", "") : address;
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

    public String getAuthKeyString() {
        return authKey.getAuthKey();
    }

    public void changeAuthPass() {
        isPassEmailAuth = true;
    }

    public boolean isAuthKeyExpired() {
        return authKey.isExpired();
    }

    public boolean isAuthKeyMatched(String authKey) {
        return this.authKey.isMatched(authKey);
    }

}
