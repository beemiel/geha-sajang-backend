package com.incense.gehasajang.domain.host;

import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("sub")
@Getter
public class SubHost extends Host {

    private boolean subHostStatus;

}
