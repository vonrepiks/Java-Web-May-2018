package org.softuni.dtos.sales;

import org.softuni.dtos.cars.CarDto;
import org.softuni.dtos.customers.CustomerNameDto;

public class SaleDto {

    private long id;

    private double discount;

    private CarDto car;

    private CustomerNameDto customer;

    private double saleWithDiscount;

    private double saleWithoutDiscount;

    public SaleDto() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getDiscount() {
        return this.discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public CarDto getCar() {
        return this.car;
    }

    public void setCar(CarDto car) {
        this.car = car;
    }

    public CustomerNameDto getCustomer() {
        return this.customer;
    }

    public void setCustomer(CustomerNameDto customer) {
        this.customer = customer;
    }

    public double getSaleWithDiscount() {
        return this.saleWithDiscount;
    }

    public void setSaleWithDiscount(double saleWithDiscount) {
        this.saleWithDiscount = saleWithDiscount;
    }

    public double getSaleWithoutDiscount() {
        return this.saleWithoutDiscount;
    }

    public void setSaleWithoutDiscount(double saleWithoutDiscount) {
        this.saleWithoutDiscount = saleWithoutDiscount;
    }
}
