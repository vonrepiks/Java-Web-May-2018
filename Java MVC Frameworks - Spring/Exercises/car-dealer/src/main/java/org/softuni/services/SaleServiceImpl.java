package org.softuni.services;

import org.modelmapper.ModelMapper;
import org.softuni.dtos.cars.CarDto;
import org.softuni.dtos.customers.CustomerNameDto;
import org.softuni.dtos.sales.CreateFinalSaleDto;
import org.softuni.dtos.sales.LazySaleDto;
import org.softuni.dtos.sales.SaleDto;
import org.softuni.entities.Car;
import org.softuni.entities.Customer;
import org.softuni.entities.Sale;
import org.softuni.repositories.CarRepository;
import org.softuni.repositories.CustomerRepository;
import org.softuni.repositories.SaleRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;

    private final CarRepository carRepository;

    private final CustomerRepository customerRepository;

    private final ModelMapper modelMapper;

    public SaleServiceImpl(SaleRepository saleRepository, CarRepository carRepository, CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.saleRepository = saleRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SaleDto findSaleById(long id) {
        Object sale = this.saleRepository.findSaleById(id);

        Object[] saleObject = (Object[]) sale;

        SaleDto saleDto = new SaleDto();
        CarDto carDto = new CarDto();
        CustomerNameDto customerNameDto = new CustomerNameDto();

        carDto.setMake((String) saleObject[1]);
        carDto.setModel((String) saleObject[2]);
        carDto.setTravelledDistance(((BigInteger) saleObject[3]).longValue());

        customerNameDto.setName((String) saleObject[4]);

        saleDto.setId(((BigInteger) saleObject[0]).longValue());
        saleDto.setCar(carDto);
        saleDto.setCustomer(customerNameDto);
        saleDto.setDiscount((Double) saleObject[6]);
        saleDto.setSaleWithDiscount(((Double) saleObject[5]) - (((Double) saleObject[5]) * saleDto.getDiscount()));
        saleDto.setSaleWithoutDiscount(((Double) saleObject[5]));

        return saleDto;
    }

    @Override
    public Set<LazySaleDto> allSales() {
        List<Sale> sales = this.saleRepository.findAll();

        Set<LazySaleDto> lazySaleDtos = new LinkedHashSet<>();

        for (Sale sale : sales) {
            lazySaleDtos.add(this.modelMapper.map(sale, LazySaleDto.class));
        }

        return lazySaleDtos;
    }

    @Override
    public void create(CreateFinalSaleDto createFinalSaleDto) {
        Sale sale = new Sale();
        Car car = this.carRepository.findById(createFinalSaleDto.getCarId()).get();
        Customer customer = this.customerRepository.findById(createFinalSaleDto.getCustomerId()).get();

        sale.setCar(car);
        sale.setCustomer(customer);
        sale.setDiscount(createFinalSaleDto.getDiscount());

        if (sale.getCustomer().isYoungDriver()) {
            sale.setDiscount(sale.getDiscount() + 5);
        }

        this.saleRepository.save(sale);
    }
}
