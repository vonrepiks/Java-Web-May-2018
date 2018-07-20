package org.softuni.dtos.users;

import java.util.Collections;
import java.util.Set;

public class UserDto {

    private String username;

    private String password;

    private Set<RoleDto> roles;

    public UserDto() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleDto> getRoles() {
        return Collections.unmodifiableSet(this.roles);
    }

    public void setRoles(Set<RoleDto> roles) {
        this.roles = roles;
    }
}
