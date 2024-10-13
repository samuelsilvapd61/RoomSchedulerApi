package samuel.oliveira.silva.roomschedulerapi.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import samuel.oliveira.silva.roomschedulerapi.infra.exception.ApiErrorEnum;
import samuel.oliveira.silva.roomschedulerapi.infra.exception.ApiException;
import samuel.oliveira.silva.roomschedulerapi.repository.UserRepository;

/** Service to load a user to authenticate. */
@Service
public class AuthenticationService implements UserDetailsService {

  @Autowired private UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var userDetails = repository.findByEmail(username);
    if (userDetails == null) {
      throw new ApiException(ApiErrorEnum.USER_DOESNT_EXIST);
    }
    return userDetails;
  }

  /**
   * This method does the same thing that loadUserByUsername does, but this method is used when the
   * system tries to get a user using the email in the token. The only difference is the exception
   * thrown, in case of user does not exist.
   *
   * @param username email
   * @return userDetails with entity User
   * @throws UsernameNotFoundException throws a ACCESS_UNAUTHORIZED exception instead
   */
  public UserDetails loadUserByJwtToken(String username) throws UsernameNotFoundException {
    var userDetails = repository.findByEmail(username);
    if (userDetails == null) {
      throw new ApiException(ApiErrorEnum.ACCESS_UNAUTHORIZED);
    }
    return userDetails;
  }
}
