package org.softuni.services;

import org.modelmapper.ModelMapper;
import org.softuni.dtos.cars.*;
import org.softuni.entities.Car;
import org.softuni.entities.Part;
import org.softuni.repositories.CarRepository;
import org.softuni.repositories.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    private final PartRepository partRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, PartRepository partRepository, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CarDto> getCarsByMakeOrderedByModelAscAndDistanceDesc(String make) {
        List<CarDto> carDtos = new ArrayList<>();
        this.carRepository.getAllCarsByMakeOrderedByModelAscAndTravelledDistanceDesc(make)
                .forEach(car ->
                        carDtos.add(this.modelMapper.map(car, CarDto.class)));
        return carDtos;
    }

    @Override
    public List<String> findAllMakes() {
        return this.carRepository.findAllMakes();
    }

    @Override
    public CarWithPartsDto getCarWithParts(long id) {
        Optional<Car> optionalCar = this.carRepository.findById(id);

        Car car = null;

        if (optionalCar.isPresent()) {
            car = optionalCar.get();
        }

        CarWithPartsDto carWithPartsDto = this.modelMapper.map(car, CarWithPartsDto.class);
        return carWithPartsDto;
    }

    @Override
    public void create(CreateCarDto createCarDto) {
        Car car = this.modelMapper.map(createCarDto, Car.class);

        this.carRepository.save(car);
    }

    @Override
    public void createWithParts(CreateCarWithParts createCarWithParts) {
        Car car = new Car();

        car.setMake(createCarWithParts.getMake());
        car.setModel(createCarWithParts.getModel());
        car.setTravelledDistance(createCarWithParts.getTravelledDistance());

        Set<Part> parts = new LinkedHashSet<>();
        createCarWithParts.getParts().forEach(id -> {
            Part part = this.partRepository.findById(Long.parseLong(id)).get();
            part.getCars().add(car);
            parts.add(part);
        });

        car.setParts(parts);

        this.carRepository.saveAndFlush(car);
    }

    @Override
    public Set<CarCreateSaleDto> findAllCars() {
        List<Car> cars = this.carRepository.findAll();

        Set<CarCreateSaleDto> carCreateSaleDtos = new LinkedHashSet<>();
        for (Car car : cars) {
            carCreateSaleDtos.add(this.modelMapper.map(car, CarCreateSaleDto.class));
        }

        return carCreateSaleDtos;
    }

    @Override
    public double getCarPrice(Long id) {
        return this.carRepository.getCarPrice(id);
    }
}
