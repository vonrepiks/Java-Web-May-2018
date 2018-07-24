package org.softuni.app.models.dto.users;

import org.softuni.app.annotations.ValidateAuthority;

public class RoleDto {

    @ValidateAuthority(acceptedValues={"USER", "MODERATOR", "ADMIN"}, message="Invalid dataType")
    private String authority;

    public RoleDto() {
    }

    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}