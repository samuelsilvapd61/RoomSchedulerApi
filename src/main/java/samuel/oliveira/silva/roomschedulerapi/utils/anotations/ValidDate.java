package samuel.oliveira.silva.roomschedulerapi.utils.anotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Personalized annotation to validate date format. */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LocalDateStringValidator.class)
public @interface ValidDate {

  /**
   * Default error message.
   *
   * @return message
   */
  String message() default "Date must be in the format yyyy-MM-dd";

  /**
   * Groups.
   *
   * @return ?
   */
  Class<?>[] groups() default {};

  /**
   * Groups.
   *
   * @return ?
   */
  Class<? extends Payload>[] payload() default {};
}
