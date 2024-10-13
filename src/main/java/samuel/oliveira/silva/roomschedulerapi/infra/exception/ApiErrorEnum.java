package samuel.oliveira.silva.roomschedulerapi.infra.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.GATEWAY_TIMEOUT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Class to concentrate personalized options to exceptions.
 */
@Getter
@SuppressWarnings(value = {"MethodParamPad", "LineLength"})
public enum ApiErrorEnum {

  // User
  USER_ID_NULL ("NotNull.userUpdateRoleRequest.userId", BAD_REQUEST, BAD_REQUEST.getReasonPhrase()),
  USER_ROLE_NULL ("NotNull.userIncludeRequest.role", BAD_REQUEST, BAD_REQUEST.getReasonPhrase()),
  USER_DOCUMENT_BLANK("NotBlank.userIncludeRequest.document", BAD_REQUEST, BAD_REQUEST.getReasonPhrase()),
  USER_EMAIL_BLANK ("NotBlank.userIncludeRequest.email", BAD_REQUEST, BAD_REQUEST.getReasonPhrase()),
  USER_PASSWORD_BLANK ("NotBlank.userIncludeRequest.password", BAD_REQUEST, BAD_REQUEST.getReasonPhrase()),
  USER_NAME_BLANK ("NotBlank.userIncludeRequest.name", BAD_REQUEST, BAD_REQUEST.getReasonPhrase()),
  USER_EMAIL_FORMAT ("Email.userIncludeRequest.email", BAD_REQUEST, BAD_REQUEST.getReasonPhrase()),
  DOCUMENT_EXISTS ("msg.document.exists", CONFLICT, CONFLICT.getReasonPhrase()),
  EMAIL_EXISTS ("msg.email.exists", CONFLICT, CONFLICT.getReasonPhrase()),
  USER_DOESNT_EXIST ("msg.user.doesnt-exist", NOT_FOUND, NOT_FOUND.getReasonPhrase()),
  USER_ROLE_DOESNT_EXIST ("msg.invalid-user-role", BAD_REQUEST, BAD_REQUEST.getReasonPhrase()),

  // Room
  ROOM_ID_NULL ("NotNull.roomUpdateRequest.id", BAD_REQUEST, BAD_REQUEST.getReasonPhrase()),
  ROOM_NAME_BLANK ("NotBlank.roomIncludeRequest.name", BAD_REQUEST, BAD_REQUEST.getReasonPhrase()),
  ROOM_DOESNT_EXIST ("msg.room.doesnt-exist", NOT_FOUND, NOT_FOUND.getReasonPhrase()),

  // Schecule
  SCHEDULE_DATE_BLANK ("NotBlank.scheduleIncludeRequest.scheduleDate", BAD_REQUEST, BAD_REQUEST.getReasonPhrase()),
  SCHEDULE_EXISTS ("msg.schedule.exists", CONFLICT, CONFLICT.getReasonPhrase()),
  SCHEDULE_DOESNT_EXIST ("msg.schedule.doesnt-exist", BAD_REQUEST, BAD_REQUEST.getReasonPhrase()),

  // Generics
  INVALID_FIELD     ("msg.invalid.field", BAD_REQUEST, BAD_REQUEST.getReasonPhrase()),
  DATE_FORMAT ("msg.date-format", BAD_REQUEST, BAD_REQUEST.getReasonPhrase()),
  INVALID_DATE ("msg.invalid-date", BAD_REQUEST, BAD_REQUEST.getReasonPhrase()),
  INVALID_JSON ("msg.invalid-json", BAD_REQUEST, BAD_REQUEST.getReasonPhrase()),
  ACCESS_UNAUTHORIZED ("msg.access-unauthorized", UNAUTHORIZED, UNAUTHORIZED.getReasonPhrase()),
  ACCESS_DENIED ("msg.access-denied", FORBIDDEN, FORBIDDEN.getReasonPhrase()),
  GATEWAY_TIMEOUT_ERROR ("msg.gateway-timeout-error", GATEWAY_TIMEOUT, GATEWAY_TIMEOUT.getReasonPhrase()),
  GENERIC_ERROR ("msg.generic-error", INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR.getReasonPhrase());

  private final String messageKey;
  private final HttpStatus status;
  private final String title;

  ApiErrorEnum(String messageKey, HttpStatus status, String title) {
    this.messageKey = messageKey;
    this.status = status;
    this.title = title;
  }

  public String getFormattedMessage() {
    return Messages.get(this.messageKey);
  }

  /**
   * Uses the ENUM name to return a real ENUM.
   *
   * @param name name of the ENUM
   * @return a ENUM
   */
  public static ApiErrorEnum valueOfOrNull(String name) {
    try {
      return ApiErrorEnum.valueOf(name);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

}
