package org.softuni.app.services;

import org.modelmapper.ModelMapper;
import org.softuni.app.models.dto.users.RegisterUserDto;
import org.softuni.app.models.dto.users.UpdateUserServiceDto;
import org.softuni.app.models.dto.users.UserDto;
import org.softuni.app.models.entities.User;
import org.softuni.app.repositories.UserRepository;
import org.softuni.app.factories.UserRoleFactory;
import org.softuni.app.models.entities.UserRole;
import org.softuni.app.repositories.UserRoleRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final UserRoleFactory userRoleFactory;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, UserRoleFactory userRoleFactory, BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.userRoleFactory = userRoleFactory;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean registerUser(RegisterUserDto registerUserDto) {

        User user = this.modelMapper.map(registerUserDto, User.class);

        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

        Set<UserRole> authorities = new HashSet<>();

        if (this.userRepository.findAll().isEmpty()) {
            authorities.add(userRoleFactory.createUserRole("ADMIN"));
        } else {
            UserRole authority = this.userRoleRepository.getUserRoleByAuthority("USER");
            authorities.add(authority);
        }

        user.setAuthorities(authorities);

        return this.userRepository.save(user) != null;
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = this.userRepository.getByUsername(username);
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public Set<UserDto> findAll() {
        List<User> users = this.userRepository.findAll();
        Set<UserDto> userDtos = new LinkedHashSet<>();

        for (User user : users) {
            userDtos.add(this.modelMapper.map(user, UserDto.class));
        }

        return userDtos;
    }

    @Override
    public UpdateUserServiceDto getUserById(String id) {
        User user = this.userRepository.findById(id).get();
        return this.modelMapper.map(user, UpdateUserServiceDto.class);
    }

    @Override
    public void update(UpdateUserServiceDto updateUserServiceDto) {
        User user = this.userRepository.findById(updateUserServiceDto.getId()).get();
        this.modelMapper.map(updateUserServiceDto, user);
        this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.getByUsername(username);
    }
}
