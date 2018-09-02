package ru.tandser.hibernate.models;

import javax.persistence.*;

@Entity
@Table(name = "card_b")
public class CardB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String cardInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    private MessageB messageB;

    public CardB() {}

    public CardB(String cardInfo) {
        this.cardInfo = cardInfo;
    }

    /* Setters and Getters */

    public Long getId() {
        return id;
    }

    public String getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(String cardInfo) {
        this.cardInfo = cardInfo;
    }

    public MessageB getMessageB() {
        return messageB;
    }

    public void setMessageB(MessageB messageB) {
        this.messageB = messageB;
    }
}