package org.softuni.controllers;

import org.softuni.annotations.PreAuthenticate;
import org.softuni.dtos.sales.SalesByCustomerDto;
import org.softuni.dtos.customers.CreateCustomerDto;
import org.softuni.dtos.customers.CustomerDto;
import org.softuni.dtos.customers.CustomersOrderDto;
import org.softuni.dtos.customers.EditCustomerDto;
import org.softuni.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/customers")
@PreAuthenticate(loggedIn = true)
public class CustomerController extends BaseController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/all/ascending")
    public ModelAndView getAllAscendingOrder() {
        CustomersOrderDto customersOrderDto = new CustomersOrderDto();
        List<CustomerDto> customers = this.customerService.getAllCustomersOrderByBirthDateAscendingOrder();
        customersOrderDto.setCustomerDtos(customers);
        customersOrderDto.setOrder("ascending");
        return super.view("views/customers/all", customersOrderDto);
    }

    @GetMapping("/all/descending")
    public ModelAndView getAllDescendingOrder() {
        CustomersOrderDto customersOrderDto = new CustomersOrderDto();
        List<CustomerDto> customers = this.customerService.getAllCustomersOrderByBirthDateDescendingOrder();
        customersOrderDto.setCustomerDtos(customers);
        customersOrderDto.setOrder("descending");
        return super.view("views/customers/all", customersOrderDto);
    }

    @GetMapping("/{id}")
    public ModelAndView getSalesByCustomer(@PathVariable(name = "id") long id) {
        SalesByCustomerDto salesByCustomerId = this.customerService.findSalesByCustomerId(id);
        return super.view("views/customers/sales", salesByCustomerId);
    }

    @GetMapping("/add")
    public ModelAndView add() {
        return super.view("views/customers/add");
    }

    @PostMapping("/add")
    public ModelAndView addConfirm(@ModelAttribute CreateCustomerDto createCustomerDto) {
        this.customerService.createCustomer(createCustomerDto);
        return super.redirect("/");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id) {
        EditCustomerDto editCustomerDto = customerService.getCustomerById(id);
        return super.view("views/customers/edit", editCustomerDto);
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editConfirm(@ModelAttribute EditCustomerDto editCustomerDto, @PathVariable("id") Long id) {
        this.customerService.editCustomer(editCustomerDto, id);
        return super.redirect("/");
    }
}
