package org.softuni.eventures.service;

import org.softuni.eventures.domain.models.services.EventServiceModel;
import org.softuni.eventures.domain.models.views.MyEventsEventViewModel;

import java.util.Set;

public interface EventService {

    boolean createEvent(EventServiceModel eventServiceModel);

    Set<EventServiceModel> findAll();

    EventServiceModel findById(String id);

    Set<MyEventsEventViewModel> findMyEventWithTicketsCount(String username);
}
