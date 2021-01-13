package com.incense.gehasajang.domain.booking;

import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class PeopleCount {

    private int femaleCount;
    private int maleCount;

    protected PeopleCount() {
    }

    public PeopleCount(int femaleCount, int maleCount) {
        this.femaleCount = femaleCount;
        this.maleCount = maleCount;
    }

}
