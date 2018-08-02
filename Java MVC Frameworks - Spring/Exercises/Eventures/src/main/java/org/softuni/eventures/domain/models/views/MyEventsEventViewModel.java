package org.softuni.eventures.domain.models.views;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MyEventsEventViewModel {

    private String name;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private BigDecimal ticketsCount;

    public MyEventsEventViewModel() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    public BigDecimal getTicketsCount() {
        return this.ticketsCount;
    }

    public void setTicketsCount(BigDecimal ticketsCount) {
        this.ticketsCount = ticketsCount;
    }
}
