package org.softuni.dtos.sales;

import org.softuni.dtos.cars.CarCreateSaleDto;
import org.softuni.dtos.customers.CustomerCreateSaleDto;

import java.util.Set;

public class CreateSaleDtoViewModel {

    private CreateSaleDto createSaleDto;

    private Set<CarCreateSaleDto> carCreateSaleDtos;

    private Set<CustomerCreateSaleDto> customerCreateSaleDtos;

    public CreateSaleDtoViewModel() {
    }

    public CreateSaleDto getCreateSaleDto() {
        return this.createSaleDto;
    }

    public void setCreateSaleDto(CreateSaleDto createSaleDto) {
        this.createSaleDto = createSaleDto;
    }

    public Set<CarCreateSaleDto> getCarCreateSaleDtos() {
        return this.carCreateSaleDtos;
    }

    public void setCarCreateSaleDtos(Set<CarCreateSaleDto> carCreateSaleDtos) {
        this.carCreateSaleDtos = carCreateSaleDtos;
    }

    public Set<CustomerCreateSaleDto> getCustomerCreateSaleDtos() {
        return this.customerCreateSaleDtos;
    }

    public void setCustomerCreateSaleDtos(Set<CustomerCreateSaleDto> customerCreateSaleDtos) {
        this.customerCreateSaleDtos = customerCreateSaleDtos;
    }
}
