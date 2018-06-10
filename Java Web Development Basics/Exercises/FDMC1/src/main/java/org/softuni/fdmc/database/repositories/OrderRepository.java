package org.softuni.fdmc.database.repositories;

import org.softuni.fdmc.database.entities.Order;

import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OrderRepository extends BaseRepository implements CrudRepository<Order> {
    @Override
    public void create(Order order) throws RollbackException {
        super.executeAction(result -> {
            super.entityManager.persist(order);
        });
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = (List<Order>) super.executeAction(result -> {
            result.setResult(super.entityManager.createNativeQuery("SELECT * FROM orders", Order.class)
                    .getResultList()
                    .stream()
                    .collect(Collectors.toList()));

        }).getResult();

        return orders;
    }

    @Override
    public Order findById(String id) {
        return null;
    }

    @Override
    public void update(Order order) {
        super.executeAction(result -> {
            super.entityManager.merge(order);
        });
    }

    public List<Order> findAllByClientId(String id) {
        List<Order> orders = (List<Order>) super.executeAction(result -> {
            Query nativeQuery = super.entityManager.createNativeQuery("SELECT * FROM orders AS o WHERE o.client_id = ?", Order.class);

            nativeQuery.setParameter(1, id);

            List<Order> allOrders = (List<Order>) nativeQuery.getResultList().stream().collect(Collectors.toList());

            result.setResult(allOrders.stream().sorted(Comparator.comparing(Order::getMadeOn)).collect(Collectors.toList()));
        }).getResult();

        return orders;
    }
}
