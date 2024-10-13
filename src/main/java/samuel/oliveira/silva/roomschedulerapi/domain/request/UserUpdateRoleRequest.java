package samuel.oliveira.silva.roomschedulerapi.domain.request;

import jakarta.validation.constraints.NotNull;
import samuel.oliveira.silva.roomschedulerapi.domain.UserRole;

/**
 * Body to update user.
 *
 * @param userId userId
 * @param role role
 */
public record UserUpdateRoleRequest(
    @NotNull
    Long userId,

    @NotNull
    UserRole role

) {
}
