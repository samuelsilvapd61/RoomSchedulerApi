package samuel.oliveira.silva.roomschedulerapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samuel.oliveira.silva.roomschedulerapi.domain.User;
import samuel.oliveira.silva.roomschedulerapi.domain.request.UserIncludeRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.request.UserUpdateRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.response.UserResponse;
import samuel.oliveira.silva.roomschedulerapi.repository.UserRepository;
import samuel.oliveira.silva.roomschedulerapi.service.EntityActionExecutor;
import samuel.oliveira.silva.roomschedulerapi.service.UserService;

/**
 * Business and logical actions of user.
 */
@Service
public class UserServiceImpl implements UserService, EntityActionExecutor {

  @Autowired
  UserRepository repository;

  @Override
  @Transactional
  public void addUser(UserIncludeRequest request) {
    var document = request.document();
    var email = request.email();
    if (documentAlreadyExists(document) || emailAlreadyExists(email)) {
      throw new RuntimeException("This user already exists.");
    }

    var newUser = new User(request);
    repository.save(newUser);
  }

  @Override
  public PagedModel<UserResponse> listUsers(Pageable pagination) {
    return new PagedModel<>(repository.findAll(pagination).map(UserResponse::new));
  }

  @Override
  @Transactional
  public void updateUser(UserUpdateRequest request) {
    executeActionIfEntityExists(
        request.id(),
        repository,
        user -> user.updateUser(request),
        "This user does not exist."
    );
  }

  @Override
  @Transactional
  public void removeUser(Long id) {
    executeActionIfEntityExists(
        id,
        repository,
        user -> repository.deleteById(user.getId()),
        "This user does not exist."
    );
  }

  @Override
  public void userExistsOrThrowException(Long id) {
    executeActionIfEntityExists(
        id,
        repository,
        user -> {},
        "This user does not exist."
    );
  }

  private boolean documentAlreadyExists(String document) {
    var existingUser = repository.findUserByDocument(document);
    return existingUser.isPresent();
  }

  private boolean emailAlreadyExists(String email) {
    var existingUser = repository.findUserByEmail(email);
    return existingUser.isPresent();
  }

}
