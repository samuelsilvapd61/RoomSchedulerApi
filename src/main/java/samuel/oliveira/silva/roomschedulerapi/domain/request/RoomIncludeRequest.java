package samuel.oliveira.silva.roomschedulerapi.domain.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Body to add a room.
 */
public record RoomIncludeRequest(@NotBlank String name) {
}
