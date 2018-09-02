package ru.tandser.hibernate.models;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "message_a")
@DynamicInsert
public class MessageA extends Message {

    @Column(name = "author_a", nullable = false, columnDefinition = "VARCHAR DEFAULT 'default'")
    private String authorA;

    @OneToMany(mappedBy = "messageA", cascade = CascadeType.PERSIST)
    private Set<CardA> cardsA = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "imagesA", joinColumns = @JoinColumn(name = "message_id"))
    @Column(name = "filename")
    protected Set<String> imagesA = new HashSet<>();

    public MessageA() {}

    public MessageA(String text) {
        super(text);
    }

    /* Setters and Getters */

    public String getAuthorA() {
        return authorA;
    }

    public void setAuthorA(String authorA) {
        this.authorA = authorA;
    }

    public Set<CardA> getCardsA() {
        return cardsA;
    }

    public void setCardsA(Set<CardA> cardsA) {
        this.cardsA = cardsA;
    }

    public Set<String> getImagesA() {
        return imagesA;
    }

    public void setImagesA(Set<String> imagesA) {
        this.imagesA = imagesA;
    }
}