package samuel.oliveira.silva.roomschedulerapi.domain.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import samuel.oliveira.silva.roomschedulerapi.domain.Schedule;

/**
 * Body to return a schedule.
 */
public record ScheduleResponse(
    Long roomId,
    String roomName,
    LocalDate scheduleDate,
    LocalDateTime scheduleInclusionDate
) {

  /**
   * Using entity to instantiate a response.
   *
   * @param schedule schedule
   */
  public ScheduleResponse(Schedule schedule) {
    this(
        schedule.getRoom().getId(),
        schedule.getRoom().getName(),
        schedule.getScheduleDate(),
        schedule.getInclusionDate()
    );
  }
}
