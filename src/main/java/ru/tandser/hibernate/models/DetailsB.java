package ru.tandser.hibernate.models;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class DetailsB extends Details {

    @Column(name = "details_b")
    private String detailsB;

    public DetailsB() {}

    public DetailsB(String info, String comment, String detailsB) {
        super(info, comment);
        this.detailsB = detailsB;
    }

    /* Setters and Getters */

    public String getDetailsB() {
        return detailsB;
    }

    public void setDetailsB(String detailsB) {
        this.detailsB = detailsB;
    }
}