package samuel.oliveira.silva.roomschedulerapi.repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import samuel.oliveira.silva.roomschedulerapi.domain.Schedule;
import samuel.oliveira.silva.roomschedulerapi.domain.ScheduleId;

/** Access to the schedules table in the database. */
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, ScheduleId> {

  @Query(
      value = "SELECT * FROM schedules s " + "WHERE s.user_id = ?1 " + "AND s.schedule_date >= ?2",
      nativeQuery = true)
  Page<Schedule> findNextSchedulesByUserId(Long userId, LocalDate date, Pageable pagination);

  Boolean existsByUserIdAndRoomIdAndScheduleDate(Long userId, Long roomId, LocalDate scheduleDate);

  @Query(
      value =
          "SELECT DATE(s.schedule_date) FROM schedules s "
              + "WHERE s.room_id = ?1 "
              + "AND s.schedule_date >= ?2",
      nativeQuery = true)
  List<Date> findNextSchedulesByRoomId(Long roomId, LocalDate scheduleDate);

  @Query(value =
      "SELECT users.email FROM users "
          + "INNER JOIN schedules ON users.id = schedules.user_id "
          + "WHERE schedules.room_id = ?1 AND schedules.schedule_date >= ?2",
      nativeQuery = true)
  List<String> listUsersWithExistingSchedulesForThisRoom(Long id, LocalDate scheduleDate);
}
