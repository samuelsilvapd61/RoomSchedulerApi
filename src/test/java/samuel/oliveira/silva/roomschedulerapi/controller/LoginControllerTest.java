package samuel.oliveira.silva.roomschedulerapi.controller;

import static org.junit.jupiter.api.Assertions.*;
import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.Path.PATH_LOGIN;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import samuel.oliveira.silva.roomschedulerapi.domain.User;
import samuel.oliveira.silva.roomschedulerapi.domain.UserRole;
import samuel.oliveira.silva.roomschedulerapi.domain.request.LoginRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.response.JwtToken;
import samuel.oliveira.silva.roomschedulerapi.infra.exception.ApiErrorDto;
import samuel.oliveira.silva.roomschedulerapi.infra.exception.ApiErrorEnum;
import samuel.oliveira.silva.roomschedulerapi.infra.exception.ApiException;
import samuel.oliveira.silva.roomschedulerapi.infra.security.AuthenticationService;
import samuel.oliveira.silva.roomschedulerapi.infra.security.TokenService;

class LoginControllerTest extends AbstractTestController {

  @InjectMocks LoginController loginController;
  @Mock private AuthenticationService authenticationService;
  @Mock private AuthenticationManager manager;
  @Mock private TokenService tokenService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    super.setupMvc(loginController);
  }

  @Test
  void given_validLogin_when_login_then_returnToken() throws Exception {
    // Arrange
    var loginRequest = new LoginRequest("email@email", "password");
    var jsonBody = toJson(loginRequest);
    var requestBuilder =
        MockMvcRequestBuilders.post(PATH_LOGIN)
            .content(jsonBody)
            .characterEncoding(StandardCharsets.UTF_8)
            .contentType(MediaType.APPLICATION_JSON);

    var user = User.builder().role(UserRole.ROLE_USER).build();
    var userToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    Mockito.when(manager.authenticate(Mockito.any())).thenReturn(userToken);
    Mockito.when(tokenService.generateJwtToken((User) userToken.getPrincipal()))
        .thenReturn(UUID.randomUUID().toString());

    // Act
    var mvcResult = mvc.perform(requestBuilder).andReturn();

    // Assert
    Mockito.verify(tokenService, Mockito.only()).generateJwtToken(Mockito.any(User.class));
    var tokenJwt = toObject(mvcResult.getResponse().getContentAsString(), JwtToken.class);
    Assertions.assertNotNull(tokenJwt.token());
  }

  @Test
  void given_invalidLogin_when_login_then_throwException() throws Exception {
    // Arrange
    var loginRequest = new LoginRequest("email@email", "password");
    var jsonBody = toJson(loginRequest);
    var requestBuilder =
        MockMvcRequestBuilders.post(PATH_LOGIN)
            .content(jsonBody)
            .characterEncoding(StandardCharsets.UTF_8)
            .contentType(MediaType.APPLICATION_JSON);

    Mockito.when(manager.authenticate(Mockito.any())).thenThrow(new ApiException(ApiErrorEnum.USER_DOESNT_EXIST));

    // Act
    var mvcResult = mvc.perform(requestBuilder).andReturn();

    // Assert
    Mockito.verify(manager, Mockito.only()).authenticate(Mockito.any());
    Mockito.verify(tokenService, Mockito.never()).generateJwtToken(Mockito.any());
  }

  @Test
  void given_invalidEmail_when_login_then_throwException() throws Exception {
    // Arrange
    var loginRequest = new LoginRequest("email", "password");
    var jsonBody = toJson(loginRequest);
    var requestBuilder =
        MockMvcRequestBuilders.post(PATH_LOGIN)
            .content(jsonBody)
            .characterEncoding(StandardCharsets.UTF_8)
            .contentType(MediaType.APPLICATION_JSON);

    // Act
    var mvcResult = mvc.perform(requestBuilder).andReturn();

    // Assert
    Mockito.verify(manager, Mockito.never()).authenticate(Mockito.any());
    Mockito.verify(tokenService, Mockito.never()).generateJwtToken(Mockito.any());

    var error = toObject(mvcResult.getResponse().getContentAsString(), ApiErrorDto.class);
    assertError(ApiErrorEnum.USER_EMAIL_FORMAT, error);

  }

}
