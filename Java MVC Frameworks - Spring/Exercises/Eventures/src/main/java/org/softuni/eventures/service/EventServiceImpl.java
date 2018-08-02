package org.softuni.eventures.service;

import org.modelmapper.ModelMapper;
import org.softuni.eventures.domain.entities.Event;
import org.softuni.eventures.domain.entities.User;
import org.softuni.eventures.domain.models.services.EventServiceModel;
import org.softuni.eventures.domain.models.views.MyEventsEventViewModel;
import org.softuni.eventures.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private final UserService userService;

    private final ModelMapper modelMapper;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, UserService userService, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean createEvent(EventServiceModel eventServiceModel) {
        Event event = this.modelMapper.map(eventServiceModel, Event.class);

        try {
            this.eventRepository.save(event);
        } catch (Exception ignored) {
            return false;
        }

        return true;
    }

    @Override
    public Set<EventServiceModel> findAll() {
        return this.eventRepository
                .findAll()
                .stream()
                .map(event -> this.modelMapper.map(event, EventServiceModel.class))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public EventServiceModel findById(String id) {
        Event event = this.eventRepository.findById(id).get();

        return this.modelMapper.map(event, EventServiceModel.class);
    }

    @Override
    public Set<MyEventsEventViewModel> findMyEventWithTicketsCount(String username) {
        String userId = ((User)this.userService.loadUserByUsername(username)).getId();
        List<Object> myEvents = this.eventRepository.findMyEventsWithTicketsCount(userId);

        Set<MyEventsEventViewModel> myEventsEventViewModels = new LinkedHashSet<>();

        for (Object myEvent : myEvents) {
            Object[] myEventArgs = (Object[]) myEvent;

            MyEventsEventViewModel myEventsEventViewModel = new MyEventsEventViewModel();
            myEventsEventViewModel.setName((String) myEventArgs[0]);
            myEventsEventViewModel.setStartDateTime(((Timestamp)myEventArgs[1]).toLocalDateTime());
            myEventsEventViewModel.setEndDateTime(((Timestamp)myEventArgs[2]).toLocalDateTime());
            myEventsEventViewModel.setTicketsCount((BigDecimal) myEventArgs[3]);

            myEventsEventViewModels.add(myEventsEventViewModel);
        }

        return myEventsEventViewModels;
    }
}
