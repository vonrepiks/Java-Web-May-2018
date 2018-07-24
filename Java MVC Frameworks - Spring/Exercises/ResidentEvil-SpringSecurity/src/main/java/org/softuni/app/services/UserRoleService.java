package org.softuni.app.services;

import org.softuni.app.models.dto.users.RoleServiceDto;

public interface UserRoleService {

    RoleServiceDto findRoleByName(String name);
}
