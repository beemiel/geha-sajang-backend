package com.incense.gehasajang.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String city;

    private String street;

    private String postcode;

    private String detail;

    protected Address() {
    }

    public Address(String city, String street, String postcode, String detail) {
        this.city = city;
        this.street = street;
        this.postcode = postcode;
        this.detail = detail;
    }
}
