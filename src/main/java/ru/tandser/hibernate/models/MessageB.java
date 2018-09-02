package ru.tandser.hibernate.models;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "message_b")
@DynamicInsert
public class MessageB extends Message {

    @Column(name = "author_b", nullable = false, columnDefinition = "VARCHAR DEFAULT 'default'")
    private String authorB;

    @OneToMany(mappedBy = "messageB", cascade = CascadeType.PERSIST)
    private Set<CardB> cardsB = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "imagesB", joinColumns = @JoinColumn(name = "message_id"))
    @Column(name = "filename")
    protected Set<String> imagesB = new HashSet<>();

    public MessageB() {}

    public MessageB(String text) {
        super(text);
    }

    /* Setters and Getters */

    public String getAuthorB() {
        return authorB;
    }

    public void setAuthorB(String authorB) {
        this.authorB = authorB;
    }

    public Set<CardB> getCardsB() {
        return cardsB;
    }

    public void setCardsB(Set<CardB> cardsB) {
        this.cardsB = cardsB;
    }

    public Set<String> getImagesB() {
        return imagesB;
    }

    public void setImagesB(Set<String> imagesB) {
        this.imagesB = imagesB;
    }
}