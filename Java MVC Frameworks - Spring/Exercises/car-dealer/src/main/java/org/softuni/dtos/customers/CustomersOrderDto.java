package org.softuni.dtos.customers;

import java.util.List;

public class CustomersOrderDto {

    private List<CustomerDto> customerDtos;

    private String order;

    public CustomersOrderDto() {
    }

    public List<CustomerDto> getCustomerDtos() {
        return this.customerDtos;
    }

    public void setCustomerDtos(List<CustomerDto> customerDtos) {
        this.customerDtos = customerDtos;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
