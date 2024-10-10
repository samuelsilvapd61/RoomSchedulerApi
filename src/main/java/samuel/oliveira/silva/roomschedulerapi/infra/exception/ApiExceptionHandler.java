package samuel.oliveira.silva.roomschedulerapi.infra.exception;

import static java.time.LocalDateTime.now;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import samuel.oliveira.silva.roomschedulerapi.domain.exception.ApiErrorDto;
import samuel.oliveira.silva.roomschedulerapi.domain.exception.ApiErrorEnum;
import samuel.oliveira.silva.roomschedulerapi.domain.exception.ApiException;

/** Interceptor for exceptions. Personalizes and returns clean responses for errors. */
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  public static final String SUFFIX_ENUM_ERROR = ".enum-error";

  record StringLength(String value, int length) {}

  /**
   * Handler to intercept ApiException.
   * Usually happens when an exception is thrown manually in the code.
   *
   * @param ex exception
   * @param request request
   * @return a ApiErrorDto
   */
  @ExceptionHandler(ApiException.class)
  public ResponseEntity<Object> handleApiException(ApiException ex, WebRequest request) {
    var apiError = buildApiError(ex);
    var status = HttpStatus.valueOf(apiError.getStatus());
    return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatusCode statusCode,
      WebRequest request) {

    var root = ExceptionUtils.getRootCause(ex);
    if (root instanceof ApiException exception) {
      return handleApiException(exception, request);
    }

    var apiError = buildApiError(ApiErrorEnum.INVALID_JSON);
    var status = HttpStatus.valueOf(apiError.getStatus());
    return handleExceptionInternal(ex, apiError, new HttpHeaders(), status, request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    ObjectError error = fetchObjectError(ex);
    String[] codes = fetchErrorCodes(error);
    String messageKey = fetchExistingKeyMessageOrDefault(codes);
    var apiError =
        Optional.ofNullable(messageKey)
            .map(key -> key.concat(SUFFIX_ENUM_ERROR))
            .map(Messages::get)
            .map(ApiErrorEnum::valueOfOrNull)
            .map(ApiExceptionHandler::buildApiError)
            .orElseGet(() -> buildApiError(ApiErrorEnum.INVALID_FIELD));
    return ResponseEntity.badRequest().body(apiError);
  }

  private ObjectError fetchObjectError(MethodArgumentNotValidException ex) {
    List<ObjectError> allErrors = new ArrayList<>(ex.getBindingResult().getAllErrors());
    return allErrors.stream()
        .filter(a -> Objects.nonNull(a.getDefaultMessage()))
        .min(Comparator.comparing(DefaultMessageSourceResolvable::getDefaultMessage))
        .orElseThrow();
  }

  private String[] fetchErrorCodes(ObjectError error) {
    if (Objects.isNull(error.getCodes())) {
      return new String[] {};
    }
    var className = error.getObjectName();
    return Arrays.stream(error.getCodes())
        .filter(code -> code.contains(className))
        .map(code -> buildMessageCodes(className, code))
        .flatMap(Collection::stream)
        .toArray(String[]::new);
  }

  private String fetchExistingKeyMessageOrDefault(String[] codes) {
    String keyMessage = findExistingKeyMessage(codes);
    return Optional.ofNullable(keyMessage)
        .orElseGet(() -> ArrayUtils.isNotEmpty(codes) ? codes[0] : null);
  }

  private String findExistingKeyMessage(String[] errorCodes) {
    List<StringLength> codes =
        Arrays.stream(errorCodes)
            .map(strLen -> new StringLength(strLen, strLen.length()))
            .sorted(Comparator.comparingInt(StringLength::length).reversed())
            .toList();

    for (var code : codes) {
      var message = Messages.getOrNull(code.value);
      if (Objects.nonNull(message)) {
        return code.value;
      }
    }
    return null;
  }

  private List<String> buildMessageCodes(String className, String code) {
    return List.of(code, className);
  }

  public static ApiErrorDto buildApiError(ApiException ex) {
    return buildApiError(ex.getMessage(), ex.getTitle(), ex.getHttpStatus());
  }

  public static ApiErrorDto buildApiError(ApiErrorEnum errorEnum) {
    return buildApiError(
        errorEnum.getFormattedMessage(), errorEnum.getTitle(), errorEnum.getStatus());
  }

  /**
   * Build a ApiErrorDto using the usual exception data.
   *
   * @param message exception description
   * @param title exception title
   * @param httpStatus httpstatus
   * @return a ApiErrorDto
   */
  public static ApiErrorDto buildApiError(String message, String title, HttpStatus httpStatus) {
    return ApiErrorDto.builder()
        .title(title)
        .detail(message)
        .status(httpStatus.value())
        .timestamp(now())
        .build();
  }
}
