package samuel.oliveira.silva.roomschedulerapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import samuel.oliveira.silva.roomschedulerapi.domain.Room;

/**
 * Access to the rooms table in the database.
 */
public interface RoomRepository extends JpaRepository<Room, Long> {

  @Query(value = "SELECT * FROM rooms r WHERE r.name like %?1%", nativeQuery = true)
  Page<Room> findAllByName(String name, Pageable pagination);

}
