package org.softuni.app.dto;

import org.hibernate.validator.constraints.Length;
import org.softuni.app.annotations.CreatorName;
import org.softuni.app.annotations.DateBefore;

import javax.validation.constraints.*;
import java.util.Set;

public class VirusCreateDto {

    @NotEmpty(message = "Name cannot be empty.")
    @Size(min = 3, max = 10, message = "Name must be in range [3 - 10] symbols.")
    private String name;

    @NotEmpty(message = "Description cannot be empty.")
    @Size(min = 5, max = 100, message = "Description must be in range [5 - 100] symbols.")
    private String description;

    @Length(max = 50, message = "Side effects must be in range [0 - 50] symbols.")
    private String sideEffects;

    @CreatorName(message = "Invalid creator")
    private String creator;

    private boolean deadly;

    private boolean curable;

    @NotNull(message = "Mutation cannot be null")
    private String mutation;

    @Min(value = 0, message = "Turnover rate min value must be 0.")
    @Max(value = 100, message = "Turnover rate max value must be 100.")
    private int turnoverRate;

    @Min(value = 1, message = "Hours until turn mutation min value must be 1.")
    @Max(value = 12, message = "Hours until turn mutation max value must be 12.")
    private int hoursUntilTurn;

    @NotNull(message = "Magnitude cannot be null")
    private String magnitude;

    @DateBefore(message = "Date must before today.")
    private String releasedOn;

    @NotEmpty(message = "You must select capitals")
    private Set<String> capitals;

    public VirusCreateDto() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSideEffects() {
        return this.sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public boolean isDeadly() {
        return this.deadly;
    }

    public void setDeadly(boolean deadly) {
        this.deadly = deadly;
    }

    public boolean isCurable() {
        return this.curable;
    }

    public void setCurable(boolean curable) {
        this.curable = curable;
    }

    public String getMutation() {
        return this.mutation;
    }

    public void setMutation(String mutation) {
        this.mutation = mutation;
    }

    public int getTurnoverRate() {
        return this.turnoverRate;
    }

    public void setTurnoverRate(int turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    public int getHoursUntilTurn() {
        return this.hoursUntilTurn;
    }

    public void setHoursUntilTurn(int hoursUntilTurn) {
        this.hoursUntilTurn = hoursUntilTurn;
    }

    public String getMagnitude() {
        return this.magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    public String getReleasedOn() {
        return this.releasedOn;
    }

    public void setReleasedOn(String releasedOn) {
        this.releasedOn = releasedOn;
    }

    public Set<String> getCapitals() {
        return this.capitals;
    }

    public void setCapitals(Set<String> capitals) {
        this.capitals = capitals;
    }
}
