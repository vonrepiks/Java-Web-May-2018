package org.softuni.app.services;

import org.softuni.app.models.dto.RegisterUserDto;
import org.softuni.app.models.dto.UserDto;

public interface UserService {

    boolean registerUser(RegisterUserDto registerUserDto);

    UserDto getUserByUsername(String username);
}
