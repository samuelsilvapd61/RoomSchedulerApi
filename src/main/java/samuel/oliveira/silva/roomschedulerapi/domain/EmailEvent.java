package samuel.oliveira.silva.roomschedulerapi.domain;

import lombok.Getter;

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
      "Some of your schedules were removed by an ADMIN. "
          + "Be sure to verify your schedules, or schedule again.");

  private final String title;
  private final String message;

  EmailEvent(String title, String message) {
    this.title = title;
    this.message = message;
  }
}
