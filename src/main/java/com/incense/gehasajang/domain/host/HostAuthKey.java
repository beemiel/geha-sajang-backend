package com.incense.gehasajang.domain.host;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HostAuthKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "host_auth_key_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "host_id")
    private Host host;

    private LocalDateTime expirationDate;

    private String authKey;

    @Builder
    public HostAuthKey(Host host, LocalDateTime expirationDate, String authKey) {
        this.host = host;
        this.expirationDate = expirationDate;
        this.authKey = authKey;
    }

    public void hashAuthKey(BCryptPasswordEncoder passwordEncoder) {
        authKey = passwordEncoder.encode(authKey);
    }
}
