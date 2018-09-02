package ru.tandser.hibernate.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
public abstract class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(generator = Generators.ID_GENERATOR)
    @Column(name = "id")
    protected Long id;

    @NotNull
    @Column(name = "text")
    protected String text;

    @CreationTimestamp
    @Column(name = "created")
    protected LocalDateTime created;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "message_type")
    protected MessageType messageType;

    protected Address address;

//    @Convert(converter = AmountConverter.class, disableConversion = false)
    @Column(name = "amount")
    protected Amount amount;

    public Message() {}

    public Message(String text) {
        this.text = text;
    }

    /* Setters and Getters */

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }
}