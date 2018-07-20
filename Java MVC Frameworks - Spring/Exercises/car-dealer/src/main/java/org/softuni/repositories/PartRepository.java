package org.softuni.repositories;

import org.softuni.entities.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {

    @Modifying
    @Query(value = "" +
            "DELETE FROM parts_cars\n" +
            "WHERE part_id = :partId", nativeQuery = true)
    void deleteRelationsInJoinPartsCarsTable(@Param("partId") Long partId);

    @Modifying
    @Query(value = "" +
            "DELETE FROM parts\n" +
            "WHERE id = :partId", nativeQuery = true)
    void deletePartById(@Param("partId") Long partId);
}
