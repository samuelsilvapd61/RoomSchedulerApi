package samuel.oliveira.silva.roomschedulerapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {

  private EmailEvent event;
  private String destiny;
}
