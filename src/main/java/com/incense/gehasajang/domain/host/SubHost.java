package com.incense.gehasajang.domain.host;

import com.incense.gehasajang.domain.HostHouse;
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

    private boolean isActive;

    @Builder
    public SubHost(Long id, String email, String nickname, String password, String profileImage, String thumbnailImage, LocalDateTime deletedAt, List<HostHouse> hostHouses) {
        super(id, email, nickname, password, profileImage, thumbnailImage, deletedAt, hostHouses);
    }

}
