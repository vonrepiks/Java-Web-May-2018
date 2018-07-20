package org.softuni.services;

import org.softuni.dtos.cars.*;

import java.util.List;
import java.util.Set;

public interface CarService {

    List<CarDto> getCarsByMakeOrderedByModelAscAndDistanceDesc(String make);

    List<String> findAllMakes();

    CarWithPartsDto getCarWithParts(long id);

    void create(CreateCarDto createCarDto);

    void createWithParts(CreateCarWithParts createCarWithParts);

    Set<CarCreateSaleDto> findAllCars();

    double getCarPrice(Long id);
}
