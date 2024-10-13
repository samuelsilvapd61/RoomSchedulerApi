package samuel.oliveira.silva.roomschedulerapi.domain.response;

import java.time.LocalDate;
import java.util.List;

/**
 * Body to return a schedule.
 */
public record RoomSchedulesResponse(
    Long roomId,
    List<LocalDate> scheduleDates
) {}
