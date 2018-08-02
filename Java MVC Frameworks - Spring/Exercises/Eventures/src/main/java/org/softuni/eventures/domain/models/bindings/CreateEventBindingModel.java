package org.softuni.eventures.domain.models.bindings;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreateEventBindingModel {

    private String name;

    private String place;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDateTime;

    private int totalTickets;

    private BigDecimal pricePerTicket;

    public CreateEventBindingModel() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return this.place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public LocalDateTime getStartDateTime() {
        return this.startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return this.endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public int getTotalTickets() {
        return this.totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public BigDecimal getPricePerTicket() {
        return this.pricePerTicket;
    }

    public void setPricePerTicket(BigDecimal pricePerTicket) {
        this.pricePerTicket = pricePerTicket;
    }
}
