package com.incense.gehasajang.domain.host;

import com.incense.gehasajang.domain.house.HostHouse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@DiscriminatorValue("sub")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubHost extends Host {

    @Builder
    public SubHost(Long id, String account, String nickname, String password, String type, String profileImage, String thumbnailImage, boolean isActive, LocalDateTime deletedAt, List<HostHouse> hostHouses) {
        super(id, account, nickname, password, type, profileImage, thumbnailImage, isActive, deletedAt, hostHouses);
    }
}
