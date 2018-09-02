package ru.tandser.hibernate.models;

import javax.persistence.*;

@Entity
@Table(name = "card_a")
public class CardA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String cardInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    private MessageA messageA;

    public CardA() {}

    public CardA(String cardInfo) {
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

    public MessageA getMessageA() {
        return messageA;
    }

    public void setMessageA(MessageA messageA) {
        this.messageA = messageA;
    }
}