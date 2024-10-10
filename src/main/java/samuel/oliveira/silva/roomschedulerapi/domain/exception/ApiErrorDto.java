package samuel.oliveira.silva.roomschedulerapi.domain.exception;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response body for exceptions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorDto {

  private String title;
  private Integer status;
  private String detail;
  private LocalDateTime timestamp;

}
