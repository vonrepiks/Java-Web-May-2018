package org.softuni.eventures.domain.entities;

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
    private String id;

    private LocalDateTime orderedOn;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User customer;

    @Column(nullable = false)
    private int ticketsCount;

    public Order() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getOrderedOn() {
        return this.orderedOn;
    }

    public void setOrderedOn(LocalDateTime orderedOn) {
        this.orderedOn = orderedOn;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getCustomer() {
        return this.customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public int getTicketsCount() {
        return this.ticketsCount;
    }

    public void setTicketsCount(int ticketsCount) {
        this.ticketsCount = ticketsCount;
    }
}
