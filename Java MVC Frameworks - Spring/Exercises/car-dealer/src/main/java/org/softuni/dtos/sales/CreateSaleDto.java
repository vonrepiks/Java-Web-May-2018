package org.softuni.dtos.sales;

public class CreateSaleDto {

    private String car;

    private String customer;

    private long discount;

    public CreateSaleDto() {
    }

    public String getCar() {
        return this.car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getCustomer() {
        return this.customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public long getDiscount() {
        return this.discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }
}
