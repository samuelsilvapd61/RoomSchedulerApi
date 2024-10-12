package samuel.oliveira.silva.roomschedulerapi.infra.security;

import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.APPLICATION_NAME;
import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.TIME_ZONE;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import samuel.oliveira.silva.roomschedulerapi.domain.User;
import samuel.oliveira.silva.roomschedulerapi.infra.exception.ApiErrorEnum;
import samuel.oliveira.silva.roomschedulerapi.infra.exception.ApiException;

/**
 * This class handles JwtToken.
 */
@Service
public class TokenService {

  @Value("${api.security.token.secret}")
  private String secret;

  /**
   * Generates JwtToken.
   *
   * @param user user
   * @return a JwtToken
   */
  public String generateJwtToken(User user) {
    try {
      var algorithm = Algorithm.HMAC256(secret);
      return JWT.create()
          .withIssuer(APPLICATION_NAME)
          .withSubject(user.getEmail())
          .withExpiresAt(expirationDate())
          .sign(algorithm);
    } catch (Exception exception) {
      throw new ApiException(ApiErrorEnum.GENERIC_ERROR);
    }
  }

  /**
   * Extracts the subject from the JwtToken.
   *
   * @param tokenJwt token
   * @return subject, in this case, the user's email
   */
  public String getSubject(String tokenJwt) {
    try {
      var algorithm = Algorithm.HMAC256(secret);
      return JWT.require(algorithm)
          .withIssuer(APPLICATION_NAME)
          .build()
          .verify(tokenJwt)
          .getSubject();
    } catch (Exception exception) {
      throw new ApiException(ApiErrorEnum.ACCESS_UNAUTHORIZED);
    }
  }

  /**
   * Returns the actual time plus a specific authorization duration.
   *
   * @return the expiration date
   */
  private Instant expirationDate() {
    return LocalDateTime.now().plusHours(6).toInstant(ZoneOffset.of(TIME_ZONE));
  }
}
