package ru.tandser.hibernate.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class City {

    @Column(name = "zip")
    private String zip;

    @NotNull
    @Column(name = "city", nullable = false)
    private String city;

    public City() {}

    public City(String zip, String city) {
        this.zip  = zip;
        this.city = city;
    }

    /* Setters and Getters */

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}