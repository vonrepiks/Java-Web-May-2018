package org.softuni.app.repositories;

import org.softuni.app.entities.Capital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CapitalRepository extends JpaRepository<Capital, String> {

    List<Capital> findByName(String name);
}
