package org.softuni.database.repositories;

import org.softuni.database.entities.User;

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
            result.setResult(super.entityManager.createNativeQuery("SELECT * FROM users WHERE user_id = " + id, User.class));
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
