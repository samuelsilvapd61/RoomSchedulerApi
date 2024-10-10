package samuel.oliveira.silva.roomschedulerapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samuel.oliveira.silva.roomschedulerapi.domain.User;
import samuel.oliveira.silva.roomschedulerapi.domain.exception.ApiErrorEnum;
import samuel.oliveira.silva.roomschedulerapi.domain.exception.ApiException;
import samuel.oliveira.silva.roomschedulerapi.domain.request.UserIncludeRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.request.UserUpdateRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.response.UserResponse;
import samuel.oliveira.silva.roomschedulerapi.repository.UserRepository;
import samuel.oliveira.silva.roomschedulerapi.service.EntityActionExecutor;
import samuel.oliveira.silva.roomschedulerapi.service.UserService;

/** Business and logical actions of user. */
@Service
public class UserServiceImpl implements UserService, EntityActionExecutor {

  @Autowired UserRepository repository;

  @Override
  @Transactional
  public UserResponse addUser(UserIncludeRequest request) {
    var document = request.document();
    var email = request.email();
    if (documentAlreadyExists(document)) {
      throw new ApiException(ApiErrorEnum.DOCUMENT_EXISTS);
    }
    if (emailAlreadyExists(email)) {
      throw new ApiException(ApiErrorEnum.EMAIL_EXISTS);
    }

    var user = new User(request);
    var newUser = repository.save(user);
    return new UserResponse(newUser);
  }

  @Override
  public UserResponse getUser(Long id) {
    return repository
        .findById(id)
        .map(UserResponse::new)
        .orElseThrow(() -> new ApiException(ApiErrorEnum.USER_DOESNT_EXIST));
  }

  @Override
  public PagedModel<UserResponse> listUsers(Pageable pagination) {
    return new PagedModel<>(repository.findAll(pagination).map(UserResponse::new));
  }

  @Override
  public UserResponse updateUser(UserUpdateRequest request) {
    var newUser =
        executeActionIfEntityExists(
            request.id(),
            repository,
            user -> {
              user.updateUser(request);
              return repository.save(user);
            },
            new ApiException(ApiErrorEnum.USER_DOESNT_EXIST));
    return new UserResponse(newUser);
  }

  @Override
  @Transactional
  public void removeUser(Long id) {
    executeActionIfEntityExists(
        id,
        repository,
        user -> {
          repository.deleteById(user.getId());
          return null;
        },
        new ApiException(ApiErrorEnum.USER_DOESNT_EXIST));
  }

  @Override
  public void userExistsOrThrowException(Long id) {
    executeActionIfEntityExists(
        id, repository, user -> null, new ApiException(ApiErrorEnum.USER_DOESNT_EXIST));
  }

  private boolean documentAlreadyExists(String document) {
    return repository.existsByDocument(document);
  }

  private boolean emailAlreadyExists(String email) {
    return repository.existsByEmail(email);
  }
}
