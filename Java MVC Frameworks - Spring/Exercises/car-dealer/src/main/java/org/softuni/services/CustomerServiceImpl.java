package org.softuni.services;

import org.modelmapper.ModelMapper;
import org.softuni.dtos.customers.CreateCustomerDto;
import org.softuni.dtos.customers.CustomerCreateSaleDto;
import org.softuni.dtos.customers.CustomerDto;
import org.softuni.dtos.customers.EditCustomerDto;
import org.softuni.dtos.sales.SalesByCustomerDto;
import org.softuni.entities.Customer;
import org.softuni.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CustomerDto> getAllCustomersOrderByBirthDateAscendingOrder() {
        List<CustomerDto> customerDtos = new ArrayList<>();
        this.customerRepository.getCustomersByOrderByBirthDateAsc()
                .forEach(customer ->
                        customerDtos.add(this.modelMapper.map(customer, CustomerDto.class)));
        return customerDtos;
    }

    @Override
    public List<CustomerDto> getAllCustomersOrderByBirthDateDescendingOrder() {
        List<CustomerDto> customerDtos = new ArrayList<>();
        this.customerRepository.getCustomersByOrderByBirthDateDesc()
                .forEach(customer ->
                        customerDtos.add(this.modelMapper.map(customer, CustomerDto.class)));
        return customerDtos;
    }

    @Override
    public SalesByCustomerDto findSalesByCustomerId(long id) {
        Object salesByCustomerId = this.customerRepository.findSalesByCustomerId(id);
        Object[] objArray = (Object[]) salesByCustomerId;

        SalesByCustomerDto salesByCustomerDto = new SalesByCustomerDto();

        salesByCustomerDto.setName((String) objArray[0]);
        salesByCustomerDto.setCountOfSales((BigInteger) objArray[1]);
        salesByCustomerDto.setTotalSpentMoney((Double) objArray[2]);

        return salesByCustomerDto;
    }

    @Override
    public void createCustomer(CreateCustomerDto createCustomerDto) {
        Customer customer = new Customer();

        LocalDate birthDate = LocalDate.parse(createCustomerDto.getBirthDate());
        customer.setName(createCustomerDto.getName());
        customer.setBirthDate(birthDate);
        customer.setYoungDriver(LocalDate.now().getYear() - birthDate.getYear() < 19);

        this.customerRepository.save(customer);
    }

    @Override
    public EditCustomerDto getCustomerById(Long id) {
        Optional<Customer> optionalCustomer = this.customerRepository.findById(id);

        Customer customer = null;
        if (optionalCustomer.isPresent()) {
            customer = optionalCustomer.get();
        }

        if (customer == null) {
            return null;
        }

        return this.modelMapper.map(customer, EditCustomerDto.class);
    }

    @Override
    public void editCustomer(EditCustomerDto editCustomerDto, Long id) {
        Customer customer = this.customerRepository.findById(id).get();

        this.modelMapper.map(editCustomerDto, customer);

        this.customerRepository.save(customer);
    }

    @Override
    public Set<CustomerCreateSaleDto> findAllCustomers() {
        List<Customer> customers = this.customerRepository.findAll();

        Set<CustomerCreateSaleDto> customerCreateSaleDtos = new LinkedHashSet<>();
        for (Customer customer : customers) {
            customerCreateSaleDtos.add(this.modelMapper.map(customer, CustomerCreateSaleDto.class));
        }

        return customerCreateSaleDtos;
    }
}
