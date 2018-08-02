package org.softuni.eventures.factories;

import org.softuni.eventures.domain.entities.UserRole;

public final class UserRoleFactory {

    public UserRoleFactory() {}

    public final UserRole createUserRole(String authority) {
        UserRole userRole = new UserRole();

        userRole.setAuthority(authority);

        return userRole;
    }
}
