package samuel.oliveira.silva.roomschedulerapi.repository;

import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import samuel.oliveira.silva.roomschedulerapi.domain.Schedule;
import samuel.oliveira.silva.roomschedulerapi.domain.ScheduleId;

/**
 * Access to the schedules table in the database.
 */
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, ScheduleId> {

  @Query(value = "SELECT * FROM schedules s "
      + "WHERE s.user_id = ?1 AND s.schedule_date >= ?2", nativeQuery = true)
  Page<Schedule> findNextSchedulesByUserId(Long userId, LocalDate date, Pageable pagination);

}
