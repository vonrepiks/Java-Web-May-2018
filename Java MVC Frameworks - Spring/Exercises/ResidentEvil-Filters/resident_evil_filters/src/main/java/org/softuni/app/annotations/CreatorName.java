package org.softuni.app.annotations;

import org.softuni.app.validators.CreatorNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = CreatorNameValidator.class)
public @interface CreatorName {

    String message() default "Invalid creator name";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}