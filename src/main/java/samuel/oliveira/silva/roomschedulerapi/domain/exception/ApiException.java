package samuel.oliveira.silva.roomschedulerapi.domain.exception;

import io.micrometer.common.util.StringUtils;
import java.util.Optional;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Exception class used to throw personalized exceptions.
 */
@Getter
public class ApiException extends RuntimeException {

  protected HttpStatus httpStatus;
  protected String errorCode;
  protected String title;

  public ApiException(ApiErrorEnum errorEnum) {
    super(errorEnum.getFormattedMessage());
    fillFields(errorEnum.getStatus(), errorEnum.getTitle());
  }

  protected void fillFields(HttpStatus status, String title) {
    this.httpStatus = Optional.ofNullable(status)
        .orElseThrow(() -> new NullPointerException("status can't be null"));
    this.title = StringUtils.isBlank(title) ? this.httpStatus.getReasonPhrase() : title.trim();
  }

}
