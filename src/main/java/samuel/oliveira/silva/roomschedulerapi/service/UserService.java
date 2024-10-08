package samuel.oliveira.silva.roomschedulerapi.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import samuel.oliveira.silva.roomschedulerapi.domain.request.UserIncludeRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.request.UserUpdateRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.response.UserResponse;

/** Interface service. */
public interface UserService {

  UserResponse addUser(UserIncludeRequest request);

  UserResponse getUser(Long id);

  PagedModel<UserResponse> listUsers(Pageable pagination);

  UserResponse updateUser(UserUpdateRequest request);

  void removeUser(Long id);

  void userExistsOrThrowException(Long id);
}
