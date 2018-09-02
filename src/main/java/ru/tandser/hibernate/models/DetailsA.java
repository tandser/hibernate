package ru.tandser.hibernate.models;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class DetailsA extends Details {

    @Column(name = "details_a")
    private String detailsA;

    public DetailsA() {}

    public DetailsA(String info, String comment, String detailsA) {
        super(info, comment);
        this.detailsA = detailsA;
    }

    /* Setters and Getters */

    public String getDetailsA() {
        return detailsA;
    }

    public void setDetailsA(String detailsA) {
        this.detailsA = detailsA;
    }
}