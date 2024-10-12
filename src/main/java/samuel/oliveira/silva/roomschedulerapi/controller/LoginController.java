package samuel.oliveira.silva.roomschedulerapi.controller;

import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.PATH_LOGIN;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import samuel.oliveira.silva.roomschedulerapi.domain.User;
import samuel.oliveira.silva.roomschedulerapi.domain.request.LoginRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.response.JwtToken;
import samuel.oliveira.silva.roomschedulerapi.infra.security.AuthenticationService;
import samuel.oliveira.silva.roomschedulerapi.infra.security.TokenService;

/** Controller to login. */
@RestController
@RequestMapping(PATH_LOGIN)
public class LoginController {

  @Autowired private AuthenticationService authenticationService;
  @Autowired private AuthenticationManager manager;
  @Autowired private TokenService tokenService;


  /**
   * Endpoint to receive login request.
   * It does authentication and returns the authentication token.
   *
   * @param request login data
   * @return a token
   */
  @PostMapping
  public ResponseEntity<JwtToken> login(@Valid @RequestBody LoginRequest request) {
    return ResponseEntity.ok(authenticateUserAndReturnJwtToken(request));
  }

  private JwtToken authenticateUserAndReturnJwtToken(LoginRequest request) {
    var authenticationToken =
        new UsernamePasswordAuthenticationToken(request.email(), request.password());
    var authentication = manager.authenticate(authenticationToken);

    return new JwtToken(tokenService.generateJwtToken((User) authentication.getPrincipal()));
  }


}
