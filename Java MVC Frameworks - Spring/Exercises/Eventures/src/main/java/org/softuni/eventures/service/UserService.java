package org.softuni.eventures.service;

import org.softuni.eventures.domain.models.services.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Set;


public interface UserService extends UserDetailsService {

    boolean registerUser(UserServiceModel userServiceModel);

    Set<UserServiceModel> findAll();

    UserServiceModel getUserById(String id);
}
