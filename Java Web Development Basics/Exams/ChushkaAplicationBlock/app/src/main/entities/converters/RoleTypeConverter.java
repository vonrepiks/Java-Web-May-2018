package entities.converters;

import entities.Role;

import javax.persistence.AttributeConverter;

public class RoleTypeConverter implements AttributeConverter<Role, String> {
    @Override
    public String convertToDatabaseColumn(Role role) {
        return role.toString();
    }

    @Override
    public Role convertToEntityAttribute(String s) {
        return Role.valueOf(s.toUpperCase());
    }
}
