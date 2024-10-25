package samuel.oliveira.silva.roomschedulerapi.domain.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Body to add a user.
 *
 * @param document document
 * @param email email
 * @param password password
 * @param name name
 */
public record UserIncludeRequest(

    @NotBlank
    String document,

    @Email
    @NotBlank
    String email,

    @NotBlank
    String password,

    @NotBlank
    String name
) {
}
