package samuel.oliveira.silva.roomschedulerapi.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import samuel.oliveira.silva.roomschedulerapi.domain.User;

/** Access to the users table in the database. */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findUserByDocument(String document);

  Optional<User> findUserByEmail(String email);

  boolean existsByDocument(String document);

  boolean existsByEmail(String email);
}
