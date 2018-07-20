package org.softuni.services;

import org.softuni.dtos.customers.CreateCustomerDto;
import org.softuni.dtos.customers.CustomerCreateSaleDto;
import org.softuni.dtos.customers.CustomerDto;
import org.softuni.dtos.sales.SalesByCustomerDto;
import org.softuni.dtos.customers.EditCustomerDto;

import java.util.List;
import java.util.Set;

public interface CustomerService {

    List<CustomerDto> getAllCustomersOrderByBirthDateAscendingOrder();

    List<CustomerDto> getAllCustomersOrderByBirthDateDescendingOrder();

    SalesByCustomerDto findSalesByCustomerId(long id);

    void createCustomer(CreateCustomerDto createCustomerDto);

    EditCustomerDto getCustomerById(Long id);

    void editCustomer(EditCustomerDto editCustomerDto, Long id);

    Set<CustomerCreateSaleDto> findAllCustomers();
}
