package org.softuni.database.repositories;

import org.softuni.database.entities.User;

import java.util.List;

public interface Repository<T> {

    void create(T entity);

    List<T> findAll();

    T findById(String id);

    void close();
}
