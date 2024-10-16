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
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import samuel.oliveira.silva.roomschedulerapi.domain.request.UserIncludeRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.request.UserUpdateRoleRequest;

/**
 * Entity user.
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "users")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String email;

  @Setter
  private String password;

  @Enumerated(EnumType.STRING)
  private UserRole role;
  private String document;
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
    this.role = UserRole.ROLE_USER;
    this.document = request.document();
    this.email = request.email();
    this.password = request.password();
    this.name = request.name();
  }

  public void updateUserRole(UserUpdateRoleRequest request) {
    this.role = request.role();
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

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(this.role.name()));
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
