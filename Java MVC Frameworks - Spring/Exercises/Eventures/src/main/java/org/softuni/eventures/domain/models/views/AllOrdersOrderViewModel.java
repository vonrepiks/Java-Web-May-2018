package org.softuni.eventures.domain.models.views;

import java.time.LocalDateTime;

public class AllOrdersOrderViewModel {

    private String eventName;

    private String customerFirstName;

    private String customerLastName;

    private LocalDateTime orderedOn;

    public AllOrdersOrderViewModel() {
    }

    public String getEventName() {
        return this.eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public LocalDateTime getOrderedOn() {
        return this.orderedOn;
    }

    public void setOrderedOn(LocalDateTime orderedOn) {
        this.orderedOn = orderedOn;
    }

    public String getFullName() {
        return this.customerFirstName + " " + this.customerLastName;
    }
}
