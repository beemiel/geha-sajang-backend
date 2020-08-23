package com.incense.gehasajang.domain.host;

import com.incense.gehasajang.domain.Address;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@DiscriminatorValue("main")
@Getter
public class MainHost extends Host {

    @Embedded
    private Address address;

    private boolean isAgreeToMarketing;

    private boolean isPassEmailAuth;

    @OneToMany(mappedBy = "host")
    private List<EmailAuthenticationCode> codes;

}
