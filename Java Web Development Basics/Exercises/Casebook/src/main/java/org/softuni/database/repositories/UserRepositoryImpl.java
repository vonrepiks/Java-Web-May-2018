package org.softuni.database.repositories;

import org.softuni.database.entities.User;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepositoryImpl extends BaseRepository {

    @Override
    public void create(User entity) {
        super.executeAction(result -> {
            super.entityManager.persist(entity);
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
            Query nativeQuery = super.entityManager.createNativeQuery("SELECT * FROM users WHERE user_id = ?");

            nativeQuery.setParameter(1, id);
            Object queryResult = nativeQuery.getSingleResult();
            Object[] results = (Object[]) queryResult;
            User searchedUser = new User();
            searchedUser.setId((String) results[0]);
            searchedUser.setEmail((String) results[1]);
            searchedUser.setPassword((String) results[2]);

            result.setResult(searchedUser);
        }).getResult();

        return user;
    }

    @Override
    public User getUserByEmail(String email) throws NoResultException {
        User user = (User) super.executeAction(result -> {
            Query nativeQuery = super.entityManager.createNativeQuery("SELECT * FROM users WHERE email = ?");

            nativeQuery.setParameter(1, email);
            Object queryResult = nativeQuery.getSingleResult();
            Object[] results = (Object[]) queryResult;
            User searchedUser = new User();
            searchedUser.setId((String) results[0]);
            searchedUser.setEmail((String) results[1]);
            searchedUser.setPassword((String) results[2]);

            result.setResult(searchedUser);
        }).getResult();

        return user;
    }

    @Override
    public void addFriend(User user, User friend) {
        super.executeAction(result -> {
            user.addFriend(friend);
            friend.addFriend(user);

            super.entityManager.merge(user);
            super.entityManager.merge(friend);
        });
    }
}
