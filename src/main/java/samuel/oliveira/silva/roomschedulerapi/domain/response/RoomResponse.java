package samuel.oliveira.silva.roomschedulerapi.domain.response;

import java.io.Serializable;
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
) implements Serializable {

  public RoomResponse(Room room) {
    this(room.getId(), room.getName(), room.getInclusionDate(), room.getLastUpdateDate());
  }

}
