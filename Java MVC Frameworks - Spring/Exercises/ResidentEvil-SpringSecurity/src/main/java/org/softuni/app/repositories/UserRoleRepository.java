package org.softuni.app.repositories;

import org.softuni.app.models.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, String> {

    UserRole getUserRoleByAuthority(String authority);
}
