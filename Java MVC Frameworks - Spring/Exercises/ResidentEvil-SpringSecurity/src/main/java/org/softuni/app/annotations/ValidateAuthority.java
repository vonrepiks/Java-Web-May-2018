package org.softuni.app.annotations;

import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateAuthority {

    String[] acceptedValues();

    String message() default "Wrong authority value!!!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
