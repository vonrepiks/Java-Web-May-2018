package org.softuni.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.annotations.PreAuthenticate;
import org.softuni.dtos.cars.CarDto;
import org.softuni.dtos.cars.CarWithPartsDto;
import org.softuni.dtos.cars.CreateCarDto;
import org.softuni.dtos.cars.CreateCarWithParts;
import org.softuni.dtos.parts.EditPartDto;
import org.softuni.dtos.parts.PartCreateCarDto;
import org.softuni.services.CarService;
import org.softuni.services.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/cars")
@PreAuthenticate(loggedIn = true)
public class CarController extends BaseController {

    private final CarService carService;

    private final PartService partService;

    private final ModelMapper modelMapper;

    @Autowired
    public CarController(CarService carService, PartService partService, ModelMapper modelMapper) {
        this.carService = carService;
        this.partService = partService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/makes")
    public ModelAndView getAllMakes() {
        List<String> allMakes = this.carService.findAllMakes();
        return super.view("views/cars/makes", allMakes);
    }

    @GetMapping("/{make}")
    public ModelAndView getAllAscendingOrder(@PathVariable(name = "make") String make) {
        List<CarDto> cars = this.carService.getCarsByMakeOrderedByModelAscAndDistanceDesc(make);
        return super.view("views/cars/by-make", cars);
    }

    @GetMapping("/{id}/parts")
    public ModelAndView getCarWithParts(@PathVariable(name = "id") long id) {
        CarWithPartsDto carWithPartsDto = this.carService.getCarWithParts(id);
        return super.view("views/cars/car-with-parts", carWithPartsDto);
    }

    @GetMapping("/add")
    public ModelAndView add() {
        return super.view("views/cars/add");
    }

    @PostMapping("/add")
    public ModelAndView addConfirm(@ModelAttribute CreateCarDto createCarDto) {
        this.carService.create(createCarDto);
        return super.redirect("makes");
    }

    @GetMapping("/add-parts")
    public ModelAndView addWithParts() {
        Set<EditPartDto> allParts = this.partService.findAllParts();
        Set<PartCreateCarDto> partCreateCarDtos = new LinkedHashSet<>();
        for (EditPartDto editPartDto : allParts) {
            partCreateCarDtos.add(this.modelMapper.map(editPartDto, PartCreateCarDto.class));
        }
        return super.view("views/cars/add-with-parts", partCreateCarDtos);
    }

    @PostMapping("/add-parts")
    public ModelAndView addWithPartsConfirm(@ModelAttribute CreateCarWithParts createCarWithParts) {
        this.carService.createWithParts(createCarWithParts);
        return super.redirect("makes");
    }
}
