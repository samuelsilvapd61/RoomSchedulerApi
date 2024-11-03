package samuel.oliveira.silva.roomschedulerapi.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import samuel.oliveira.silva.roomschedulerapi.messaging.EmailEvent;

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
