package samuel.oliveira.silva.roomschedulerapi.domain.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import samuel.oliveira.silva.roomschedulerapi.domain.UserRole;

/**
 * Body to update user.
 *
 * @param id id
 * @param role role
 * @param name name
 */
public record UserUpdateRequest(
    @NotNull
    Long id,

    @NotNull
    UserRole role,

    @NotEmpty
    String name
) {
}
