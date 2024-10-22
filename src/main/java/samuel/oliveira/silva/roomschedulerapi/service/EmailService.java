package samuel.oliveira.silva.roomschedulerapi.service;

import samuel.oliveira.silva.roomschedulerapi.domain.EmailEvent;

public interface EmailService {

  String sendEmail(EmailEvent event, String emailDestiny);

}
