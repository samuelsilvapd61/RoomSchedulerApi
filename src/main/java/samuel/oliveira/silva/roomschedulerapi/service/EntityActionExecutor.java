package samuel.oliveira.silva.roomschedulerapi.service;

import java.util.function.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;

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
  default <R extends JpaRepository<E, I>, E, I> void executeActionIfEntityExists(
      I id, R repository, Consumer<E> action, String errorMessage) {
    var existentEntity = repository.findById(id);
    existentEntity.ifPresentOrElse(
        action,
        () -> {
          throw new RuntimeException(errorMessage);
        });
  }
}
