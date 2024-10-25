package samuel.oliveira.silva.roomschedulerapi.domain;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import samuel.oliveira.silva.roomschedulerapi.domain.request.ScheduleRemoveRequest;

/**
 * This class represents the primary key of the Schedule. This is a double primary key.
 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleId implements Serializable {
  private Long room;
  private LocalDate scheduleDate;

  public ScheduleId(ScheduleRemoveRequest request) {
    this.room = request.roomId();
    this.scheduleDate = LocalDate.parse(request.scheduleDate());
  }
}

