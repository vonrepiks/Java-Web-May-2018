package org.softuni.eventures.domain.models.services;

import java.time.LocalDateTime;

public class OrderServiceModel {

    private EventServiceModel event;

    private UserServiceModel customer;

    private LocalDateTime orderedOn;

    private int ticketsCount;

    public OrderServiceModel() {
    }

    public EventServiceModel getEvent() {
        return this.event;
    }

    public void setEvent(EventServiceModel event) {
        this.event = event;
    }

    public UserServiceModel getCustomer() {
        return this.customer;
    }

    public void setCustomer(UserServiceModel customer) {
        this.customer = customer;
    }

    public LocalDateTime getOrderedOn() {
        return this.orderedOn;
    }

    public void setOrderedOn(LocalDateTime orderedOn) {
        this.orderedOn = orderedOn;
    }

    public int getTicketsCount() {
        return this.ticketsCount;
    }

    public void setTicketsCount(int ticketsCount) {
        this.ticketsCount = ticketsCount;
    }
}
