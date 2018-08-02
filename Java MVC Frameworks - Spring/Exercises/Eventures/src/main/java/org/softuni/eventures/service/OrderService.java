package org.softuni.eventures.service;

import org.softuni.eventures.domain.models.services.OrderServiceModel;

import java.util.Set;

public interface OrderService {

    boolean createOrder(OrderServiceModel orderServiceModel);

    Set<OrderServiceModel> findAll();
}
