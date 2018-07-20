package org.softuni.controllers;

import org.softuni.annotations.PreAuthenticate;
import org.softuni.dtos.cars.CarCreateSaleDto;
import org.softuni.dtos.customers.CustomerCreateSaleDto;
import org.softuni.dtos.sales.*;
import org.softuni.services.CarService;
import org.softuni.services.CustomerService;
import org.softuni.services.SaleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/sales")
@PreAuthenticate(loggedIn = true)
public class SaleController extends BaseController {

    private Map<String, Object> cache;

    private final SaleService saleService;

    private final CustomerService customerService;

    private final CarService carService;

    public SaleController(SaleService saleService, CustomerService customerService, CarService carService) {
        this.saleService = saleService;
        this.customerService = customerService;
        this.carService = carService;
        this.cache = new HashMap<>();
    }

    @GetMapping("")
    public ModelAndView allSales() {
        Set<LazySaleDto> lazySaleDtos = this.saleService.allSales();
        return super.view("views/sales/all", lazySaleDtos);
    }

    @GetMapping("/{id}")
    public ModelAndView findSaleById(@PathVariable(name = "id") long id) {
        SaleDto saleDto = this.saleService.findSaleById(id);

        return super.view("views/sales/details", saleDto);
    }

    @GetMapping("/add")
    public ModelAndView add(@ModelAttribute CreateSaleDto createSaleDto) {
        this.cache.clear();
        CreateSaleDtoViewModel createSaleDtoViewModel = new CreateSaleDtoViewModel();
        createSaleDtoViewModel.setCreateSaleDto(createSaleDto);
        Set<CarCreateSaleDto> carCreateSaleDtos = this.carService.findAllCars();
        createSaleDtoViewModel.setCarCreateSaleDtos(carCreateSaleDtos);
        Set<CustomerCreateSaleDto> customerCreateSaleDtos = this.customerService.findAllCustomers();
        createSaleDtoViewModel.setCustomerCreateSaleDtos(customerCreateSaleDtos);
        this.cache.putIfAbsent("saleDtoViewModel", createSaleDtoViewModel);
        return super.view("views/sales/add", createSaleDtoViewModel);
    }

    @PostMapping("/add/review")
    public ModelAndView addReview(@ModelAttribute CreateSaleDto createSaleDto) {
        CreateSaleDtoViewModel cachedSaleDto = (CreateSaleDtoViewModel) this.cache.get("saleDtoViewModel");

        CreateFinalSaleDto createFinalSaleDto = new CreateFinalSaleDto();
        createFinalSaleDto.setCarId(Long.parseLong(createSaleDto.getCar()));
        createFinalSaleDto.setCustomerId(Long.parseLong(createSaleDto.getCustomer()));
        createFinalSaleDto.setDiscount(createSaleDto.getDiscount());
        this.cache.put("finalSale", createFinalSaleDto);

        CustomerCreateSaleDto customerCreateSaleDto = cachedSaleDto
                .getCustomerCreateSaleDtos()
                .stream()
                .filter(customer -> String.valueOf(customer.getId()).equals(createSaleDto.getCustomer()))
                .findFirst()
                .orElse(null);

        CarCreateSaleDto carCreateSaleDto = cachedSaleDto.getCarCreateSaleDtos()
                .stream()
                .filter(car -> String.valueOf(car.getId()).equals(createSaleDto.getCar()))
                .findFirst()
                .orElse(null);

        CreateSaleReviewDto createSaleReviewDto = new CreateSaleReviewDto();
        createSaleReviewDto.setCar(carCreateSaleDto);
        createSaleReviewDto.setCustomer(customerCreateSaleDto);
        createSaleReviewDto.setDiscount(createSaleDto.getDiscount());

        double price = this.carService.getCarPrice(Long.parseLong(createSaleDto.getCar()));

        createSaleReviewDto.getCar().setPrice(price);

        return super.view("views/sales/add-review", createSaleReviewDto);
    }

    @PostMapping("/add")
    public ModelAndView addConfirm() {
        CreateFinalSaleDto createFinalSaleDto = (CreateFinalSaleDto) this.cache.get("finalSale");
        this.cache.clear();
        this.saleService.create(createFinalSaleDto);
        return super.redirect("/");
    }
}
