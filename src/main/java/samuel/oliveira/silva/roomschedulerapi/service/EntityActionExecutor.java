package samuel.oliveira.silva.roomschedulerapi.service;

import java.util.function.Function;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/** Class to provide support methods. */
public interface EntityActionExecutor {

  /**
   * This method verifies if the Entity exists, if yes, executes an action.
   *
   * @param id id
   * @param repository repository
   * @param action action to be executed
   * @param errorMessage errorMessage to be thrown
   * @param <R> repository
   * @param <E> entity
   * @param <I> id, can be a Long or a Class representing an id
   */
  default <R extends JpaRepository<E, I>, E, I> E executeActionIfEntityExists(
      I id, R repository, Function<E, E> action, String errorMessage) {
    E response;
    var entity = repository.findById(id);
    if (entity.isPresent()) {
      response = action.apply(entity.get());
    } else {
      throw new RuntimeException(errorMessage);
    }
    return response;
  }
}
