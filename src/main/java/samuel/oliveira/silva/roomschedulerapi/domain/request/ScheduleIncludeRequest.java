package samuel.oliveira.silva.roomschedulerapi.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import samuel.oliveira.silva.roomschedulerapi.utils.anotations.ValidDate;

/** Body to add a schedule. */
public record ScheduleIncludeRequest(
    @NotNull
    Long userId,

    @NotNull
    Long roomId,

    @NotBlank
    @ValidDate
    String scheduleDate
) {}
