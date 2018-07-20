package org.softuni.dtos.sales;

import org.softuni.dtos.cars.CarCreateSaleDto;
import org.softuni.dtos.customers.CustomerCreateSaleDto;

public class CreateSaleReviewDto {

    private CustomerCreateSaleDto customer;

    private CarCreateSaleDto car;

    private Long discount;

    public CreateSaleReviewDto() {
    }

    public CustomerCreateSaleDto getCustomer() {
        return this.customer;
    }

    public void setCustomer(CustomerCreateSaleDto customer) {
        this.customer = customer;
    }

    public CarCreateSaleDto getCar() {
        return this.car;
    }

    public void setCar(CarCreateSaleDto car) {
        this.car = car;
    }

    public Long getDiscount() {
        return this.discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }
}
