package org.softuni.eventures.service;

import org.modelmapper.ModelMapper;
import org.softuni.eventures.domain.entities.User;
import org.softuni.eventures.domain.entities.UserRole;
import org.softuni.eventures.domain.models.services.UserServiceModel;
import org.softuni.eventures.factories.UserRoleFactory;
import org.softuni.eventures.repository.UserRepository;
import org.softuni.eventures.repository.UserRoleRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    public boolean registerUser(UserServiceModel userServiceModel) {

        User user = this.modelMapper.map(userServiceModel, User.class);

        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

        Set<UserRole> authorities = new HashSet<>();

        if (this.userRepository.findAll().isEmpty()) {
            authorities.add(userRoleFactory.createUserRole("ADMIN"));
        } else {
            UserRole authority = this.userRoleRepository.getUserRoleByAuthority("USER");
            authorities.add(authority);
        }

        user.setAuthorities(authorities);

        try {
            this.userRepository.save(user);
        } catch (Exception ignored) {
            return false;
        }

        return true;
    }

    @Override
    public Set<UserServiceModel> findAll() {
        return this.userRepository
                .findAll()
                .stream()
                .map(user -> this.modelMapper.map(user, UserServiceModel.class))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public UserServiceModel getUserById(String id) {
        User user = this.userRepository.findById(id).get();
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.getByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User with current username is not found!");
        }

        return user;
    }
}
