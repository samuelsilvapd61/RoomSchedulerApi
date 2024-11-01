package samuel.oliveira.silva.roomschedulerapi.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import samuel.oliveira.silva.roomschedulerapi.controller.AbstractTestController;
import samuel.oliveira.silva.roomschedulerapi.domain.EmailRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.User;
import samuel.oliveira.silva.roomschedulerapi.domain.User.UserBuilder;
import samuel.oliveira.silva.roomschedulerapi.domain.UserRole;
import samuel.oliveira.silva.roomschedulerapi.domain.request.UserIncludeRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.request.UserUpdateRoleRequest;
import samuel.oliveira.silva.roomschedulerapi.infra.exception.ApiErrorEnum;
import samuel.oliveira.silva.roomschedulerapi.infra.exception.ApiException;
import samuel.oliveira.silva.roomschedulerapi.infra.security.SecurityConfiguration;
import samuel.oliveira.silva.roomschedulerapi.messaging.EmailDispatcher;
import samuel.oliveira.silva.roomschedulerapi.repository.UserRepository;

class UserServiceImplTest extends AbstractTestController {

  @InjectMocks UserServiceImpl userService;
  @Mock UserRepository repository;
  @Mock SecurityConfiguration securityConfiguration;
  @Mock EmailDispatcher emailDispatcher;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    super.setupMvc(userService);
  }

  @Test
  void given_validUser_when_addUser_then_addUserAndSendEmail() {
    // Arrange
    var request = defaultUserIncludeRequest();
    var newUser = defaultUser().build();
    when(repository.existsByDocument(request.document())).thenReturn(false);
    when(repository.existsByEmail(request.email())).thenReturn(false);
    when(securityConfiguration.passwordEncoder()).thenReturn(new BCryptPasswordEncoder());
    when(repository.save(any(User.class))).thenReturn(newUser);

    // Act
    userService.addUser(request);

    // Assert
    verify(repository, Mockito.atLeastOnce()).save(any(User.class));
    verify(emailDispatcher, only())
        .sendEmailToRabbitMq(any(EmailRequest.class));
  }

  @Test
  void given_existingEmail_when_addUser_then_throwException() {
    // Arrange
    var request = defaultUserIncludeRequest();
    var newUser = defaultUser().build();
    when(repository.existsByDocument(request.document())).thenReturn(false);
    when(repository.existsByEmail(request.email())).thenReturn(true);
    when(securityConfiguration.passwordEncoder()).thenReturn(new BCryptPasswordEncoder());
    when(repository.save(any(User.class))).thenReturn(newUser);

    // Act
    ApiException exception = assertThrows(ApiException.class, () -> userService.addUser(request));

    // Assert
    verify(repository, Mockito.never()).save(any(User.class));
    verify(emailDispatcher, Mockito.never())
        .sendEmailToRabbitMq(any(EmailRequest.class));
    assertError(ApiErrorEnum.EMAIL_EXISTS, exception);
  }

  @Test
  void given_existingDocument_when_addUser_then_throwException() {
    // Arrange
    var request = defaultUserIncludeRequest();
    var newUser = defaultUser().build();
    when(repository.existsByDocument(request.document())).thenReturn(true);
    when(repository.existsByEmail(request.email())).thenReturn(false);
    when(securityConfiguration.passwordEncoder()).thenReturn(new BCryptPasswordEncoder());
    when(repository.save(any(User.class))).thenReturn(newUser);

    // Act
    ApiException exception = assertThrows(ApiException.class, () -> userService.addUser(request));

    // Assert
    verify(repository, Mockito.never()).save(any(User.class));
    verify(emailDispatcher, Mockito.never())
        .sendEmailToRabbitMq(any(EmailRequest.class));
    assertError(ApiErrorEnum.DOCUMENT_EXISTS, exception);
  }

  @Test
  void given_validUserId_when_getUser_then_returnUser() {
    // Arrange
    var userId = 1L;
    when(repository.findById(userId)).thenReturn(Optional.ofNullable(defaultUser().build()));

    // Act
    var user = userService.getUser(userId);

    // Assert
    assertNotNull(user);
  }

  @Test
  void given_nonexistentUserId_when_getUser_then_returnUser() {
    // Arrange
    var userId = 1L;
    when(repository.findById(userId)).thenReturn(Optional.empty());

    // Act
    ApiException exception = assertThrows(ApiException.class, () -> userService.getUser(userId));

    // Assert
    assertError(ApiErrorEnum.USER_DOESNT_EXIST, exception);
  }

  @Test
  void given_email_when_listUsers_then_returnUserList() {
    // Arrange
    var email = "email@email.com";
    var userList = List.of(defaultUser().build(), defaultUser().build());
    var pageable = PageRequest.of(0, 10);
    var page = new PageImpl<>(userList, pageable, userList.size());

    when(repository.findAllByEmail(email, pageable)).thenReturn(page);

    // Act
    var result = userService.listUsers(email, pageable);

    // Assert
    assertNotNull(result);
    assertTrue(ArrayUtils.isNotEmpty(result.getContent().toArray()));
    assertEquals(userList.size(), result.getContent().size());
  }

  @Test
  void given_userUpdateRequest_when_updateUser_then_returnUpdatedUser() {
    // Arrange
    var updateRequest = new UserUpdateRoleRequest(1L, UserRole.ROLE_USER);
    var user = defaultUser().build();
    when(repository.findById(updateRequest.userId())).thenReturn(Optional.ofNullable(user));
    when(repository.save(user)).thenReturn(user);

    // Act
    userService.updateUser(updateRequest);

    // Assert
    verify(repository, atLeastOnce()).save(user);
  }

  @Test
  void given_userUpdateRequest_when_updateUser_then_throwException() {
    // Arrange
    var updateRequest = new UserUpdateRoleRequest(1L, UserRole.ROLE_USER);
    var user = defaultUser().build();
    when(repository.findById(updateRequest.userId())).thenReturn(Optional.empty());
    when(repository.save(user)).thenReturn(user);

    // Act
    ApiException exception = assertThrows(ApiException.class, () ->  userService.updateUser(updateRequest));


    // Assert
    verify(repository, never()).save(user);
    assertError(ApiErrorEnum.USER_DOESNT_EXIST, exception);
  }

  @Test
  void given_userId_when_removeUser_then_removeUser() {
    // Arrange
    var userId = 1L;
    var user = defaultUser().build();
    when(repository.findById(userId)).thenReturn(Optional.ofNullable(user));

    // Act
    userService.removeUser(userId);

    // Assert
    verify(repository, atLeastOnce()).deleteById(user.getId());
  }

  @Test
  void given_nonexistentUserId_when_removeUser_then_throwException() {
    // Arrange
    var userId = 1L;
    var user = defaultUser().build();
    when(repository.findById(userId)).thenReturn(Optional.empty());

    // Act
    ApiException exception = assertThrows(ApiException.class, () ->  userService.removeUser(userId));


    // Assert
    verify(repository, never()).deleteById(user.getId());
    assertError(ApiErrorEnum.USER_DOESNT_EXIST, exception);
  }

  @Test
  void given_userId_when_getExistentUser_then_returnUser() {
    // Arrange
    var userId = 1L;
    var savedUser = defaultUser().build();
    when(repository.findById(userId)).thenReturn(Optional.ofNullable(savedUser));

    // Act
    var user = userService.getUserExistsOrThrowException(userId);

    // Assert
    assertNotNull(user);
  }

  @Test
  void given_nonExistentUserId_when_getExistentUser_then_throwException() {
    // Arrange
    var userId = 1L;
    when(repository.findById(userId)).thenReturn(Optional.empty());

    // Act
    ApiException exception = assertThrows(ApiException.class, () ->  userService.getUserExistsOrThrowException(userId));

    // Assert
    assertError(ApiErrorEnum.USER_DOESNT_EXIST, exception);
  }

  private UserIncludeRequest defaultUserIncludeRequest() {
    return new UserIncludeRequest(
        UUID.randomUUID().toString(),
        "email@email.com",
        "password",
        "UserName");
  }

  private UserBuilder defaultUser() {
    return User.builder()
        .id(1L)
        .role(UserRole.ROLE_USER)
        .name("UserName")
        .document("UserDocument")
        .email("email@email.com")
        .password("userpassword")
        .inclusionDate(LocalDateTime.now())
        .lastUpdateDate(LocalDateTime.now());
  }

}
