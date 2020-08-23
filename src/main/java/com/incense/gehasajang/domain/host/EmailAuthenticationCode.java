package com.incense.gehasajang.domain.host;

import com.incense.gehasajang.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailAuthenticationCode extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_authentication_code_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "host_id")
    private Host host;

    private LocalDateTime expirationDate;

    private String code;

}
