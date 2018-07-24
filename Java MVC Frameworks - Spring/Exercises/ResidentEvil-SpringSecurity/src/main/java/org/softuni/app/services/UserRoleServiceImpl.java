package org.softuni.app.services;

import org.modelmapper.ModelMapper;
import org.softuni.app.models.dto.users.RoleServiceDto;
import org.softuni.app.models.entities.UserRole;
import org.softuni.app.repositories.UserRoleRepository;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    private final ModelMapper modelMapper;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, ModelMapper modelMapper) {
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RoleServiceDto findRoleByName(String name) {
        UserRole userRole = this.userRoleRepository.getUserRoleByAuthority(name);
        return this.modelMapper.map(userRole, RoleServiceDto.class);
    }
}
