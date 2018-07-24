package org.softuni.app.factories;

import org.softuni.app.models.entities.UserRole;

public final class UserRoleFactory {
    public UserRoleFactory() {}

    public final UserRole createUserRole(String authority) {
        UserRole userRole = new UserRole();

        userRole.setAuthority(authority);

        return userRole;
    }
}
