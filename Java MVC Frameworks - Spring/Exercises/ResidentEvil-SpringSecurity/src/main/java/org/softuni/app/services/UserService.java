package org.softuni.app.services;

import org.softuni.app.models.dto.users.RegisterUserDto;
import org.softuni.app.models.dto.users.UpdateUserServiceDto;
import org.softuni.app.models.dto.users.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Set;

public interface UserService extends UserDetailsService {

    boolean registerUser(RegisterUserDto registerUserDto);

    UserDto getUserByUsername(String username);

    Set<UserDto> findAll();

    UpdateUserServiceDto getUserById(String id);

    void update(UpdateUserServiceDto updateUserServiceDto);
}
