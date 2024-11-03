package samuel.oliveira.silva.roomschedulerapi.messaging;

import lombok.Getter;

/**
 * Class to represent the email that is going to be sent to the user.
 */
@Getter
public enum EmailEvent {
  INCLUDED_USER("Account creation", "Your account was succesfully created!"),
  REMOVED_USER("Account removal", "Your account was deleted."),
  UPDATED_ROOM(
      "Room update",
      "Some of your scheduled rooms changed their names. "
          + "Be sure to verify your schedules, or schedule again."),
  REMOVED_ROOM(
      "Room removal",
      "Some of your scheduled rooms were removed. "
          + "Be sure to verify your schedules, or schedule again."),
  REMOVED_SCHEDULE(
      "Schedule removal",
      "One of your schedules was removed. If it was not you that do this, it was an ADMIN."
          + "Be sure to verify your schedules, or schedule again.");

  private final String title;
  private final String message;

  EmailEvent(String title, String message) {
    this.title = title;
    this.message = message;
  }
}
