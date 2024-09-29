package samuel.oliveira.silva.roomschedulerapi.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Body to add a room.
 */
public record RoomUpdateRequest(

    @NotNull
    Long id,
    @NotBlank
    String name

) {
}
