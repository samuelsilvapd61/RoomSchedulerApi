package samuel.oliveira.silva.roomschedulerapi.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import samuel.oliveira.silva.roomschedulerapi.domain.request.UserIncludeRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.request.UserUpdateRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.response.UserResponse;

/**
 * Interface service.
 */
public interface UserService {

  void addUser(UserIncludeRequest request);

  PagedModel<UserResponse> listUsers(Pageable pagination);

  void updateUser(UserUpdateRequest request);

  void removeUser(Long id);

  void userExistsOrThrowException(Long id);

}
