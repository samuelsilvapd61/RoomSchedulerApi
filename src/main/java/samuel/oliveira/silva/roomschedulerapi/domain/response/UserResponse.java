package samuel.oliveira.silva.roomschedulerapi.domain.response;

import java.time.LocalDateTime;
import samuel.oliveira.silva.roomschedulerapi.domain.User;
import samuel.oliveira.silva.roomschedulerapi.domain.UserRole;

/**
 * Body to return a user.
 *
 * @param id             id
 * @param role           role
 * @param document       document
 * @param email          email
 * @param name           name
 * @param inclusionDate  inclusionDate
 * @param lastUpdateDate lastUpdateDate
 */
public record UserResponse(
    Long id,
    UserRole role,
    String document,
    String email,
    String name,
    LocalDateTime inclusionDate,
    LocalDateTime lastUpdateDate
) {

  /**
   * Using entity to instantiate a reponse.
   *
   * @param user user
   */
  public UserResponse(User user) {
    this(
        user.getId(),
        user.getRole(),
        user.getDocument(),
        user.getEmail(),
        user.getName(),
        user.getInclusionDate(),
        user.getLastUpdateDate()
    );
  }

}

