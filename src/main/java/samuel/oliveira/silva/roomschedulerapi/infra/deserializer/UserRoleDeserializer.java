package samuel.oliveira.silva.roomschedulerapi.infra.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import samuel.oliveira.silva.roomschedulerapi.domain.UserRole;
import samuel.oliveira.silva.roomschedulerapi.domain.exception.ApiErrorEnum;
import samuel.oliveira.silva.roomschedulerapi.domain.exception.ApiException;

/**
 * Validates if the value received in the request matches one of the possible values in UserRole.
 * If matches, returns the ENUM value.
 * If not, throws an exception.
 */
public class UserRoleDeserializer extends JsonDeserializer<UserRole> {

  @Override
  public UserRole deserialize(JsonParser jsonParser, DeserializationContext context)
      throws IOException, JsonProcessingException {
    String value = jsonParser.getText().toUpperCase();
    try {
      return UserRole.valueOf(value);
    } catch (IllegalArgumentException e) {
      throw new ApiException(ApiErrorEnum.USER_ROLE_DOESNT_EXIST);
    }
  }
}
