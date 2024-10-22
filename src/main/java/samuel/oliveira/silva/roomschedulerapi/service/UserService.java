package samuel.oliveira.silva.roomschedulerapi.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import samuel.oliveira.silva.roomschedulerapi.domain.User;
import samuel.oliveira.silva.roomschedulerapi.domain.request.UserIncludeRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.request.UserUpdateRoleRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.response.UserResponse;

/** Interface service. */
public interface UserService {

  UserResponse addUser(UserIncludeRequest request);

  UserResponse getUser(Long id);

  PagedModel<UserResponse> listUsers(String email, Pageable pagination);

  UserResponse updateUser(UserUpdateRoleRequest request);

  void removeUser(Long id);

  User getUserExistsOrThrowException(Long id);
}
