package com.incense.gehasajang.domain;

import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.PrePersist;

@Embeddable
@Getter
public class Address {

    private String city;

    private String street;

    private String postcode;

    private String detail;

    protected Address() {}

    public Address(String city, String street, String postcode, String detail) {
        this.city = city;
        this.street = street;
        this.postcode = postcode;
        this.detail = detail;
    }

    @PrePersist
    public void prePersist() {
        this.city = this.city == null ? "" : this.city;
        this.street = this.street == null ? "" : this.street;
        this.postcode = this.postcode == null ? "" : this.postcode;
        this.detail = this.detail == null ? "" : this.detail;
    }
}
