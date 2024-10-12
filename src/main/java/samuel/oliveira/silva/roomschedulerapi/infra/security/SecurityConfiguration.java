package samuel.oliveira.silva.roomschedulerapi.infra.security;

import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.PATH_LOGIN;
import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.PATH_USER;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import samuel.oliveira.silva.roomschedulerapi.infra.exception.ApiErrorEnum;
import samuel.oliveira.silva.roomschedulerapi.infra.exception.ApiException;
import samuel.oliveira.silva.roomschedulerapi.infra.exception.ManualExceptionHandler;

/** This class configures the access to the endpoints, demanding authentication. */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration {

  @Autowired private SecurityFilter securityFilter;
  @Autowired private ManualExceptionHandler exceptionHandler;
  @Autowired private ObjectMapper objectMapper;

  /**
   * This method demands authentication to all endpoints, and releases access only to login.
   *
   * @param http http
   * @return securityFilterChain
   * @throws Exception exception
   */
  // TODO - Tratar exceção
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf(csrf -> csrf.disable())
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            request -> {
              request.requestMatchers(HttpMethod.POST, PATH_LOGIN).permitAll();
              request.requestMatchers(HttpMethod.POST, PATH_USER).permitAll();
              request.anyRequest().authenticated();
            })
        .exceptionHandling(
            exceptions ->
                exceptions.authenticationEntryPoint(
                    (request, response, accessDeniedException) -> {
                      exceptionHandler.handleException(
                          request, response, new ApiException(ApiErrorEnum.ACCESS_UNAUTHORIZED));
                    }))
        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
