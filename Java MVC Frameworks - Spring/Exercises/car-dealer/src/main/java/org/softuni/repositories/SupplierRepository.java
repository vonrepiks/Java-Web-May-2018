package org.softuni.repositories;

import org.softuni.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query(value = "SELECT *\n" +
            "  FROM suppliers AS s\n" +
            "WHERE s.is_importer = :isImporter", nativeQuery = true)
    List<Supplier> findAllSuppliersByImporter(@Param("isImporter") boolean isImporter);
}
