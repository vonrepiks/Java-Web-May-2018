package org.softuni.app.validators;

import org.softuni.app.annotations.ValidateAuthority;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class AuthorityValidator implements ConstraintValidator<ValidateAuthority, String> {

    private List<String> valueList;

    @Override
    public void initialize(ValidateAuthority constraintAnnotation) {
        this.valueList = new ArrayList<>();
        for(String val : constraintAnnotation.acceptedValues()) {
            this.valueList.add(val.toUpperCase());
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.valueList.contains(value.toUpperCase());
    }
}
