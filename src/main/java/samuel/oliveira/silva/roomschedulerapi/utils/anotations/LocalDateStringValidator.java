package samuel.oliveira.silva.roomschedulerapi.utils.anotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import samuel.oliveira.silva.roomschedulerapi.domain.exception.ApiErrorEnum;
import samuel.oliveira.silva.roomschedulerapi.domain.exception.ApiException;

/** This class implements a validation to Strings who have to have a date format. */
public class LocalDateStringValidator implements ConstraintValidator<ValidDate, String> {

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @Override
  public boolean isValid(String date, ConstraintValidatorContext context) {
    if (date == null || date.isEmpty()) {
      return true;
    }

    try {
      LocalDate.parse(date, DATE_FORMATTER);
      return true;
    } catch (DateTimeParseException e) {
      throw new ApiException(ApiErrorEnum.DATE_FORMAT);
    }
  }
}
