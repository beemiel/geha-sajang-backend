package com.incense.gehasajang.domain.host;

import com.incense.gehasajang.domain.BaseTimeEntity;
import com.incense.gehasajang.domain.HostHouse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public abstract class Host extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "host_id")
    private Long id;

    private String email;

    private String nickname;

    private String password;

    private String profileImage;

    private String thumbnailImage;

    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "host")
    private List<HostHouse> hostHouses;

    public void hashPassword(BCryptPasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }

    public boolean checkPassword(String password, BCryptPasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.password);
    }

}
