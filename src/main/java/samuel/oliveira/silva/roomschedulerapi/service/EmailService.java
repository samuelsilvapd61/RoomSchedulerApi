package samuel.oliveira.silva.roomschedulerapi.service;

import samuel.oliveira.silva.roomschedulerapi.messaging.EmailEvent;

/**
 * Interface to service.
 */
public interface EmailService {

  void sendEmail(EmailEvent event, String emailDestiny);

}
