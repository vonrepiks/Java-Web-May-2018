package org.softuni.repositories;

import org.softuni.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = "" +
            "   SELECT s.id, c.make, c.model, c.travelled_distance, c2.name, sum(p.price), s.discount\n" +
            "     FROM sales AS s\n" +
            "LEFT JOIN customers c2\n" +
            "       ON s.customer_id = c2.id\n" +
            "LEFT JOIN cars AS c\n" +
            "       ON s.car_id = c.id\n" +
            "LEFT JOIN parts_cars AS pc\n" +
            "       ON c.id = pc.car_id\n" +
            "LEFT JOIN parts p\n" +
            "       ON pc.part_id = p.id\n" +
            "    WHERE s.id = :id\n" +
            " GROUP BY s.id;", nativeQuery = true)
    Object findSaleById(@Param("id") long id);
}
