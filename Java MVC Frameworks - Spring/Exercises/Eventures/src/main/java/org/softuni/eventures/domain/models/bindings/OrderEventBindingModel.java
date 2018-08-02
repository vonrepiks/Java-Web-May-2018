package org.softuni.eventures.domain.models.bindings;

public class OrderEventBindingModel {

    private String eventId;

    private int ticketsCount;

    public OrderEventBindingModel() {
    }

    public String getEventId() {
        return this.eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public int getTicketsCount() {
        return this.ticketsCount;
    }

    public void setTicketsCount(int ticketsCount) {
        this.ticketsCount = ticketsCount;
    }
}
