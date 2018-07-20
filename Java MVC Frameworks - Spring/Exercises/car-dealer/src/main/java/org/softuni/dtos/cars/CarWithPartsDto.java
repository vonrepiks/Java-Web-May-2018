package org.softuni.dtos.cars;

import org.softuni.dtos.parts.PartDto;

import java.util.Set;

public class CarWithPartsDto {

    private String make;

    private String model;

    private long travelledDistance;

    private Set<PartDto> parts;

    public CarWithPartsDto() {
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

    public Set<PartDto> getParts() {
        return this.parts;
    }

    public void setParts(Set<PartDto> parts) {
        this.parts = parts;
    }
}
