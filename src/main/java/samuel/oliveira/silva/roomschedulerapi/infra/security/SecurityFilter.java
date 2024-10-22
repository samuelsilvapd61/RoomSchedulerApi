package samuel.oliveira.silva.roomschedulerapi.infra.security;

import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.Authentication.AUTHENTICATION;
import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.Authentication.BEARER;
import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.Generics.EMPTY_STRING;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import samuel.oliveira.silva.roomschedulerapi.infra.exception.ApiErrorEnum;
import samuel.oliveira.silva.roomschedulerapi.infra.exception.ApiException;
import samuel.oliveira.silva.roomschedulerapi.infra.exception.ManualExceptionHandler;

/**
 * This class configures a filter before the request reaches any endpoint. A filter is like a layer.
 * It's possible to do a previous validation before enter the endpoints. In this class, I validate
 * the JwtToken and set an authentication to the client. Here, I had to return the exceptions
 * "manually", because the ApiExceptionHandler doesn't capture the exceptions automatically here.
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {

  @Autowired private TokenService tokenService;
  @Autowired private AuthenticationService authenticationService;
  @Autowired private ManualExceptionHandler manualExceptionHandler;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    try {
      var tokenJwt = getTokenFromHeader(request);

      if (tokenJwt != null) {
        var subject = tokenService.getSubject(tokenJwt);
        var user = authenticationService.loadUserByJwtToken(subject);

        var authentication =
            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (ApiException ex) {
      manualExceptionHandler.handleException(request, response, ex);
      return;
    } catch (Exception ex) {
      manualExceptionHandler.handleException(
          request, response, new ApiException(ApiErrorEnum.ACCESS_UNAUTHORIZED));
      return;
    }

    filterChain.doFilter(request, response);
  }

  private String getTokenFromHeader(HttpServletRequest request) {
    var authorizationHeader = request.getHeader(AUTHENTICATION);
    if (authorizationHeader != null) {
      return authorizationHeader.replace(BEARER, EMPTY_STRING);
    }

    return null;
  }
}
