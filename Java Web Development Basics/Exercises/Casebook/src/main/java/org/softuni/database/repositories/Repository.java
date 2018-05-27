package org.softuni.database.repositories;

import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import java.util.List;

public interface Repository<T> {

    void create(T entity) throws RollbackException;

    List<T> findAll();

    T findById(String id);

    void close();

    T getUserByEmail(String email) throws NoResultException;
}
