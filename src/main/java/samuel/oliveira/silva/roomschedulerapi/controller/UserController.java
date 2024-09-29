package samuel.oliveira.silva.roomschedulerapi.controller;

import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.ID;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import samuel.oliveira.silva.roomschedulerapi.domain.request.UserIncludeRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.request.UserUpdateRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.response.UserResponse;
import samuel.oliveira.silva.roomschedulerapi.service.UserService;

/**
 * Controller of User.
 */
@RestController
@RequestMapping("user")
public class UserController {

  @Autowired
  private UserService service;

  @PostMapping
  public void includeUser(@Valid @RequestBody UserIncludeRequest request) {
    service.addUser(request);
  }

  @GetMapping
  public PagedModel<UserResponse> listUsers(
      @PageableDefault(size = 10, sort = {ID}) Pageable pagination) {
    return service.listUsers(pagination);
  }

  @PutMapping
  public void updateUser(@Valid @RequestBody UserUpdateRequest request) {
    service.updateUser(request);
  }

  @DeleteMapping("/{id}")
  public void removeUser(@PathVariable Long id) {
    service.removeUser(id);
  }

}
