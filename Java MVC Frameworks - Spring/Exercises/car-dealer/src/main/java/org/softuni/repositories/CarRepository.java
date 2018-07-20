package org.softuni.repositories;

import org.softuni.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query(value = "SELECT *\n" +
            "  FROM cars AS c\n" +
            "WHERE c.make = :make\n" +
            "ORDER BY c.model ASC, c.travelled_distance DESC;", nativeQuery = true)
    List<Car> getAllCarsByMakeOrderedByModelAscAndTravelledDistanceDesc(@Param("make") String make);

    @Query(value = "SELECT DISTINCT c.make\n" +
            "  FROM cars AS c;", nativeQuery = true)
    List<String> findAllMakes();

    @Query(value = "" +
            "    SELECT sum(p.price)\n" +
            "      FROM parts AS p\n" +
            "INNER JOIN parts_cars AS pc\n" +
            "        ON p.id = pc.part_id AND pc.car_id = :carId", nativeQuery = true)
    double getCarPrice(@Param("carId") Long carId);
}
