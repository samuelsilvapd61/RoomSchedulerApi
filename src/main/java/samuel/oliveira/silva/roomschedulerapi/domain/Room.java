package samuel.oliveira.silva.roomschedulerapi.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import samuel.oliveira.silva.roomschedulerapi.domain.request.RoomIncludeRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.request.RoomUpdateRequest;

/**
 * Entity Room.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Room")
@Table(name = "rooms")
public class Room {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private LocalDateTime inclusionDate;
  private LocalDateTime lastUpdateDate;

  public Room(RoomIncludeRequest request) {
    this.id = null;
    this.name = request.name();
  }

  public void updateRoom(RoomUpdateRequest request) {
    this.name = request.name();
  }

  @PrePersist
  public void prePersist() {
    this.inclusionDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    this.lastUpdateDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
  }

  @PreUpdate
  public void preUpdate() {
    this.lastUpdateDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
  }

}
