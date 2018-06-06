package spring.services;

import spring.dtos.UserFromRegisterDto;
import spring.entities.User;

import java.util.List;

public interface UserService {

    List<UserFromRegisterDto> findAll();

    User findById(String id);

    User createOne(UserFromRegisterDto user);

    List<User> createMany(Iterable<User> users);

    User updateOne(User user);

    List<User> updateMany(Iterable<User> users);

    void deleteById(String id);

    void deleteByUser(User user);
}
