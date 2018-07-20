package org.softuni.app.validators;

import org.softuni.app.annotations.DateBefore;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateBeforeValidator implements ConstraintValidator<DateBefore, String> {
   public void initialize(DateBefore constraint) {
   }

   public boolean isValid(String date, ConstraintValidatorContext context) {
      if (date == null || date.isEmpty()) {
         return false;
      }
      LocalDate today = LocalDate.now();
      LocalDate inputDate = LocalDate.parse(date);
      return inputDate.isBefore(today);
   }
}
