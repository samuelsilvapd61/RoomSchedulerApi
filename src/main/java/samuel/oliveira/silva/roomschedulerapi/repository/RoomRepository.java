package samuel.oliveira.silva.roomschedulerapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samuel.oliveira.silva.roomschedulerapi.domain.Room;

/**
 * Access to the rooms table in the database.
 */
public interface RoomRepository extends JpaRepository<Room, Long> {
}
