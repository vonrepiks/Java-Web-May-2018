package org.softuni.fdmc.database.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(nullable = false, updatable = false)
    private String id;

    @OneToOne
    private User client;

    @OneToOne
    private Cat cat;

    @Column(name = "made_on")
    private LocalDateTime madeOn;

    public Order() {
    }

    public Order(User client, Cat cat, LocalDateTime madeOn) {
        this.client = client;
        this.cat = cat;
        this.madeOn = madeOn;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getClient() {
        return this.client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Cat getCat() {
        return this.cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    public LocalDateTime getMadeOn() {
        return this.madeOn;
    }

    public void setMadeOn(LocalDateTime madeOn) {
        this.madeOn = madeOn;
    }
}
