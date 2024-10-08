package samuel.oliveira.silva.roomschedulerapi.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import samuel.oliveira.silva.roomschedulerapi.domain.request.ScheduleIncludeRequest;

/**
 * Entity schedule.
 */
@Getter
@NoArgsConstructor
@Entity(name = "Schedule")
@Table(name = "schedules")
@IdClass(ScheduleId.class)
public class Schedule {

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Id
  @ManyToOne
  @JoinColumn(name = "room_id")
  private Room room;

  @Id
  private LocalDate scheduleDate;
  private LocalDateTime inclusionDate;

  /**
   * Using request to instantiate a Schedule entity.
   *
   * @param request request
   */
  public Schedule(ScheduleIncludeRequest request) {
    this.user = User.builder().id(request.userId()).build();
    this.room = Room.builder().id(request.roomId()).build();
    this.scheduleDate = LocalDate.parse(request.scheduleDate());
  }

  @PrePersist
  public void prePersist() {
    this.inclusionDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
  }
}

