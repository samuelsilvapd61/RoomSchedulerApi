package samuel.oliveira.silva.roomschedulerapi.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import org.springframework.transaction.annotation.Transactional;
import samuel.oliveira.silva.roomschedulerapi.domain.request.UserIncludeRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.request.UserUpdateRequest;

/**
 * Entity user.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private UserRole role;
  private String document;
  private String email;
  private String name;
  private LocalDateTime inclusionDate;
  private LocalDateTime lastUpdateDate;

  /**
   * Using request to instantiate a User entity.
   *
   * @param request UserIncludeRequest
   */
  public User(UserIncludeRequest request) {
    this.id = null;
    this.role = request.role();
    this.document = request.document();
    this.email = request.email();
    this.name = request.name();
  }

  public void updateUser(UserUpdateRequest request) {
    this.role = request.role();
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
