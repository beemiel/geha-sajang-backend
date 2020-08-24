package com.incense.gehasajang.domain.host;

import com.github.dozermapper.core.Mapping;
import com.incense.gehasajang.domain.Address;
import com.incense.gehasajang.domain.HostHouse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@DiscriminatorValue("main")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MainHost extends Host {

    @Embedded
    private Address address;

    private boolean isAgreeToMarketing;

    private boolean isPassEmailAuth;

    //TODO: 2020-08-24 원투원으로 바꿀까 -lynn
    @OneToMany(mappedBy = "host")
    private List<EmailAuthenticationCode> codes;

    @Builder
    public MainHost(Long id, String email, String nickname, String password, String profileImage, String thumbnailImage, LocalDateTime deletedAt, List<HostHouse> hostHouses, Address address, boolean isAgreeToMarketing, boolean isPassEmailAuth) {
        super(id, email, nickname, password, profileImage, thumbnailImage, deletedAt, hostHouses);
        this.address = address;
        this.isAgreeToMarketing = isAgreeToMarketing;
        this.isPassEmailAuth = isPassEmailAuth;
    }

}
