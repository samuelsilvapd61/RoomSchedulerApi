package samuel.oliveira.silva.roomschedulerapi.controller;

import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.EMAIL;
import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.EMPTY_STRING;
import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.ID;
import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.PATH_ID;
import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.PATH_USER;
import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.ROLE_ADMIN;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import samuel.oliveira.silva.roomschedulerapi.domain.request.UserIncludeRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.request.UserUpdateRoleRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.response.UserResponse;
import samuel.oliveira.silva.roomschedulerapi.service.UserService;

/** Controller of User. */
@RestController
@RequestMapping(PATH_USER)
public class UserController {

  @Autowired private UserService service;

  /**
   * Adds a new user.
   *
   * @param request new user data
   * @param uriBuilder URL to get user
   * @return a new user
   */
  @PostMapping
  public ResponseEntity<UserResponse> includeUser(
      @Valid @RequestBody UserIncludeRequest request, UriComponentsBuilder uriBuilder) {
    var user = service.addUser(request);
    var uri = uriBuilder.path(PATH_USER + PATH_ID).buildAndExpand(user.id()).toUri();
    return ResponseEntity.created(uri).body(user);
  }

  @GetMapping(PATH_ID)
  @Secured(ROLE_ADMIN)
  @SecurityRequirement(name = "bearer-key")
  public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
    return ResponseEntity.ok(service.getUser(id));
  }

  @GetMapping
  @Secured(ROLE_ADMIN)
  @SecurityRequirement(name = "bearer-key")
  public ResponseEntity<PagedModel<UserResponse>> listUsers(
      @RequestParam(value = EMAIL, defaultValue = EMPTY_STRING) String email,
      @PageableDefault(size = 10, sort = {ID}) Pageable pagination) {
    return ResponseEntity.ok(service.listUsers(email, pagination));
  }

  @PutMapping
  @Secured(ROLE_ADMIN)
  @SecurityRequirement(name = "bearer-key")
  public ResponseEntity<UserResponse> updateUser(
      @Valid @RequestBody UserUpdateRoleRequest request) {
    return ResponseEntity.ok(service.updateUser(request));
  }

  @DeleteMapping(PATH_ID)
  @Secured(ROLE_ADMIN)
  @SecurityRequirement(name = "bearer-key")
  public ResponseEntity<Void> removeUser(@PathVariable Long id) {
    service.removeUser(id);
    return ResponseEntity.noContent().build();
  }
}
