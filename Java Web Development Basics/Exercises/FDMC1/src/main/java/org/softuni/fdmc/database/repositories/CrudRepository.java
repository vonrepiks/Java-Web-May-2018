package org.softuni.fdmc.database.repositories;

import javax.persistence.RollbackException;
import java.util.List;

public interface CrudRepository<T> {

    void create(T entity) throws RollbackException;

    List<T> findAll();

    T findById(String id);

    void update(T entity);
}
