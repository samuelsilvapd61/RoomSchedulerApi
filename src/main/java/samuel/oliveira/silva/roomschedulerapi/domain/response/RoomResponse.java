package samuel.oliveira.silva.roomschedulerapi.domain.response;

import java.time.LocalDateTime;
import samuel.oliveira.silva.roomschedulerapi.domain.Room;

/**
 * Body to return a room.
 */
public record RoomResponse(
    Long id,
    String name,
    LocalDateTime inclusionDate,
    LocalDateTime lastUpdateDate
) {

  public RoomResponse(Room room) {
    this(room.getId(), room.getName(), room.getInclusionDate(), room.getLastUpdateDate());
  }

}
