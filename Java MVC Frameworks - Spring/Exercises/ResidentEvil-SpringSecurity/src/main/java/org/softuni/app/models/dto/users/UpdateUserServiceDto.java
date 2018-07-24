package org.softuni.app.models.dto.users;

import java.util.Set;

public class UpdateUserServiceDto {

    private String id;

    private Set<RoleServiceDto> authorities;

    public UpdateUserServiceDto() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<RoleServiceDto> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Set<RoleServiceDto> authorities) {
        this.authorities = authorities;
    }
}
