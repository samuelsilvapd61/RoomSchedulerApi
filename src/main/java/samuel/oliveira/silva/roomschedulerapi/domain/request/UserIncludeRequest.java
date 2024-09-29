package samuel.oliveira.silva.roomschedulerapi.domain.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import samuel.oliveira.silva.roomschedulerapi.domain.UserRole;

/**
 * Body to add a user.
 *
 * @param role role
 * @param document document
 * @param email email
 * @param name name
 */
public record UserIncludeRequest(
    @NotNull
    UserRole role,

    @NotEmpty
    String document,

    @NotEmpty
    @Email
    String email,

    @NotEmpty
    String name
) {
}
