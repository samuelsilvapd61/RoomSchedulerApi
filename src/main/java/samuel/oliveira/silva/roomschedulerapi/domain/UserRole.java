package samuel.oliveira.silva.roomschedulerapi.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import samuel.oliveira.silva.roomschedulerapi.infra.deserializer.UserRoleDeserializer;

/**
 * Roles of user.
 */
@JsonDeserialize(using = UserRoleDeserializer.class)
public enum UserRole {

  ADMIN,
  USER;

}
