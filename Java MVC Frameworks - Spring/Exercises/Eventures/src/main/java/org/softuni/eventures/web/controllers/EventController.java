package org.softuni.eventures.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.eventures.domain.entities.User;
import org.softuni.eventures.domain.models.bindings.CreateEventBindingModel;
import org.softuni.eventures.domain.models.bindings.OrderEventBindingModel;
import org.softuni.eventures.domain.models.services.EventServiceModel;
import org.softuni.eventures.domain.models.services.OrderServiceModel;
import org.softuni.eventures.domain.models.services.UserServiceModel;
import org.softuni.eventures.domain.models.views.AllEventsEventViewModel;
import org.softuni.eventures.domain.models.views.MyEventsEventViewModel;
import org.softuni.eventures.service.EventService;
import org.softuni.eventures.service.OrderService;
import org.softuni.eventures.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/events")
public class EventController extends BaseController {

    private final EventService eventService;

    private final OrderService orderService;

    private final UserService userService;

    private final ModelMapper modelMapper;

    @Autowired
    public EventController(EventService eventService, OrderService orderService, UserService userService, ModelMapper modelMapper) {
        this.eventService = eventService;
        this.orderService = orderService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/create")
    public ModelAndView create() {
        return super.view("views/events/create");
    }

    @PostMapping("/create")
    public ModelAndView createConfirm(@ModelAttribute CreateEventBindingModel createEventBindingModel) {
        EventServiceModel eventServiceModel = this.modelMapper.map(createEventBindingModel, EventServiceModel.class);
        this.eventService.createEvent(eventServiceModel);

        return super.redirect("/home");
    }

    @GetMapping("/all")
    public ModelAndView all() {
        Set<AllEventsEventViewModel> allEventsEventViewModels = this.eventService
                .findAll()
                .stream()
                .map(eventServiceModel -> this.modelMapper.map(eventServiceModel, AllEventsEventViewModel.class))
                .collect(Collectors.toCollection(LinkedHashSet::new));
        return super.view("views/events/all", allEventsEventViewModels);
    }

    @GetMapping("/my")
    public ModelAndView my(Principal principal) {
        Set<MyEventsEventViewModel> myEventsEventViewModels = this.eventService.findMyEventWithTicketsCount(principal.getName());
        return super.view("views/events/my", myEventsEventViewModels);
    }

    @PostMapping("/order")
    public ModelAndView order(@ModelAttribute OrderEventBindingModel orderEventBindingModel, Principal principal) {
        String name = principal.getName();

        User user = (User) userService.loadUserByUsername(name);

        EventServiceModel eventServiceModel = this.eventService.findById(orderEventBindingModel.getEventId());

        if (user == null || eventServiceModel == null) {
            throw new IllegalArgumentException("User or Event can not be null");
        }

        UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);

        OrderServiceModel orderServiceModel = this.modelMapper.map(orderEventBindingModel, OrderServiceModel.class);

        orderServiceModel.setCustomer(userServiceModel);
        orderServiceModel.setEvent(eventServiceModel);
        orderServiceModel.setTicketsCount(orderEventBindingModel.getTicketsCount());
        orderServiceModel.setOrderedOn(LocalDateTime.now());
        this.orderService.createOrder(orderServiceModel);

        return super.redirect("/home");
    }
}
