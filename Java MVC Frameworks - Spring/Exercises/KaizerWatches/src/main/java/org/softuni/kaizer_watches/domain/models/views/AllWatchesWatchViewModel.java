package org.softuni.kaizer_watches.domain.models.views;

import java.math.BigDecimal;

public class AllWatchesWatchViewModel {

    private String id;

    private String name;

    private BigDecimal price;

    public AllWatchesWatchViewModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
