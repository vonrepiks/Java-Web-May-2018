package org.softuni.app.services;

import org.modelmapper.ModelMapper;
import org.softuni.app.models.dto.RegisterUserDto;
import org.softuni.app.models.dto.UserDto;
import org.softuni.app.models.entities.Role;
import org.softuni.app.models.entities.User;
import org.softuni.app.repositories.RoleRepository;
import org.softuni.app.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean registerUser(RegisterUserDto registerUserDto) {

        User user = this.modelMapper.map(registerUserDto, User.class);

        Role role = this.roleRepository.getRoleByName("USER");

        if (role == null) {
            role = new Role();
            role.setName("USER");
        }

        user.addRole(role);

        return this.userRepository.save(user) != null;
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = this.userRepository.getByUsername(username);
        return this.modelMapper.map(user, UserDto.class);
    }
}
