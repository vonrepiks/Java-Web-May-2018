package org.softuni.services;

import org.softuni.dtos.users.RegisterUserDto;
import org.softuni.dtos.users.UserDto;

public interface UserService {

    boolean registerUser(RegisterUserDto registerUserDto);

    UserDto getUserByUsername(String username);
}
