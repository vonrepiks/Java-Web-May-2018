package org.softuni.repositories;

import org.softuni.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> getCustomersByOrderByBirthDateAsc();

    List<Customer> getCustomersByOrderByBirthDateDesc();

    @Query(value = "" +
            "   SELECT c.name, count(distinct s.id), sum(p.price)\n" +
            "     FROM customers AS c\n" +
            "LEFT JOIN sales AS s\n" +
            "       ON c.id = s.customer_id\n" +
            "LEFT JOIN parts_cars AS pc\n" +
            "       ON s.car_id = pc.car_id\n" +
            "LEFT JOIN parts AS p\n" +
            "       ON pc.part_id = p.id\n" +
            "    WHERE c.id = :id", nativeQuery = true)
    Object findSalesByCustomerId(@Param("id") long id);
}
