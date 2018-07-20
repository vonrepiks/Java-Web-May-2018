package org.softuni.dtos.cars;

import java.util.Set;

public class CreateCarWithParts {

    private String make;

    private String model;

    private long travelledDistance;

    private Set<String> parts;

    public CreateCarWithParts() {
    }

    public String getMake() {
        return this.make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getTravelledDistance() {
        return this.travelledDistance;
    }

    public void setTravelledDistance(long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public Set<String> getParts() {
        return this.parts;
    }

    public void setParts(Set<String> parts) {
        this.parts = parts;
    }
}
