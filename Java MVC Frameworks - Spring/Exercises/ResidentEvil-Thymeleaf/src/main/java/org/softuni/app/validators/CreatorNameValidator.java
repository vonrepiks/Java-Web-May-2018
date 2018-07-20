package org.softuni.app.validators;

import org.softuni.app.annotations.CreatorName;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CreatorNameValidator implements ConstraintValidator<CreatorName, String> {


    @Override
    public boolean isValid(String creatorName, ConstraintValidatorContext constraintValidatorContext) {
        return creatorName.equals("Corp") || creatorName.equals("corp");
    }
}
