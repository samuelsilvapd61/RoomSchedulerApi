package samuel.oliveira.silva.roomschedulerapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class representing the necessary data to send an email.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {

  private EmailEvent event;
  private String destiny;
}
