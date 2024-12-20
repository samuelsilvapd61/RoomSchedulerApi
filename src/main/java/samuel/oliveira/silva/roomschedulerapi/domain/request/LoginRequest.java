package samuel.oliveira.silva.roomschedulerapi.domain.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Body request to login.
 *
 * @param email email
 * @param password password
 */
public record LoginRequest(@Email @NotBlank String email, @NotBlank String password) {}
