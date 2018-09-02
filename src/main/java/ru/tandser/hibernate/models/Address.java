package ru.tandser.hibernate.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class Address {

    @NotNull
    private City city;

    @NotNull
    @Column(name = "street", nullable = false)
    private String street;

    public Address() {}

    public Address(City city, String street) {
        this.city   = city;
        this.street = street;
    }

    /* Setters and Getters */

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}