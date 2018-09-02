package ru.tandser.hibernate.models;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Details {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    protected Long id;

    @Column(name = "info")
    protected String info;

    @Column(name = "comment")
    protected String comment;

    public Details() {}

    public Details(String info, String comment) {
        this.info    = info;
        this.comment = comment;
    }

    /* Setters and Getters */

    public Long getId() {
        return id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}