package org.softuni.eventures.service;

import org.modelmapper.ModelMapper;
import org.softuni.eventures.domain.entities.Order;
import org.softuni.eventures.domain.models.services.OrderServiceModel;
import org.softuni.eventures.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean createOrder(OrderServiceModel orderServiceModel) {
        Order order = this.modelMapper.map(orderServiceModel, Order.class);

        try {
            this.orderRepository.save(order);
        } catch (Exception ignored) {
            return false;
        }

        return true;
    }

    @Override
    public Set<OrderServiceModel> findAll() {
        return this.orderRepository
                .findAll()
                .stream()
                .map(order -> this.modelMapper.map(order, OrderServiceModel.class))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
