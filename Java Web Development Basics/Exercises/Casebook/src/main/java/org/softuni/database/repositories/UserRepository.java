package org.softuni.database.repositories;

import org.softuni.database.entities.User;

public interface UserRepository extends Repository<User> {

    void addFriend(User user, User friend);
}
