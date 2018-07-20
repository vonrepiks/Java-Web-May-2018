package org.softuni.app.repositories;

import org.softuni.app.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {

    Role getRoleByName(String roleName);
}
