package samuel.oliveira.silva.roomschedulerapi.domain.request;

import jakarta.validation.constraints.NotNull;
import samuel.oliveira.silva.roomschedulerapi.utils.anotations.ValidDate;

/**
 * Body to add a schedule.
 */
public record ScheduleRemoveRequest(
    @NotNull
    Long roomId,

    @NotNull
    @ValidDate(message = "Date must be in the format yyyy-MM-dd and valid.")
    String scheduleDate

) {
}
