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

  // TODO - Tratar exceção
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var userDetails = repository.findByEmail(username);
    if (userDetails == null) {
      throw new ApiException(ApiErrorEnum.USER_DOESNT_EXIST);
    }
    return repository.findByEmail(username);
  }
}
