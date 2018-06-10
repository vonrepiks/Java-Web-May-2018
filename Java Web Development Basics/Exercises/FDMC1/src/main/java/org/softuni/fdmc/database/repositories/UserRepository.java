package org.softuni.fdmc.database.repositories;

import org.softuni.fdmc.database.entities.User;

import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepository extends BaseRepository implements CrudRepository<User> {

    @Override
    public void create(User user) {
        super.executeAction(result -> {
            super.entityManager.persist(user);
        });
    }

    @Override
    public List<User> findAll() {
        List<User> users = (List<User>) super.executeAction(result -> {
            result.setResult(super.entityManager.createNativeQuery("SELECT * FROM users", User.class)
                    .getResultList()
                    .stream()
                    .collect(Collectors.toList()));

        }).getResult();

        return users;
    }

    @Override
    public User findById(String id) {
        User user = (User) super.executeAction(result -> {
            Query nativeQuery = super.entityManager.createNativeQuery("SELECT u.id, u.username, u.password, u.role FROM users AS u WHERE id = ?");

            nativeQuery.setParameter(1, id);
            Object queryResult = nativeQuery.getSingleResult();
            Object[] results = (Object[]) queryResult;
            User searchedUser = new User();
            searchedUser.setId((String) results[0]);
            searchedUser.setUsername((String) results[1]);
            searchedUser.setPassword((String) results[2]);
            searchedUser.setRole((String) results[3]);

            result.setResult(searchedUser);
        }).getResult();

        return user;
    }

    @Override
    public void update(User user) {
        super.executeAction(result -> {
            super.entityManager.merge(user);
        });
    }

    public User findByUsername(String username) {
        User user = (User) super.executeAction(result -> {
            Query nativeQuery = super.entityManager.createNativeQuery("SELECT u.id, u.username, u.password, u.role FROM users AS u WHERE username = ?");

            nativeQuery.setParameter(1, username);
            Object queryResult = nativeQuery.getSingleResult();
            Object[] results = (Object[]) queryResult;
            User searchedUser = new User();
            searchedUser.setId((String) results[0]);
            searchedUser.setUsername((String) results[1]);
            searchedUser.setPassword((String) results[2]);
            searchedUser.setRole((String) results[3]);

            result.setResult(searchedUser);
        }).getResult();

        return user;
    }
}
