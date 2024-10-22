package samuel.oliveira.silva.roomschedulerapi.messaging;

import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.RabbitMq.QUEUE_EMAIL;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import samuel.oliveira.silva.roomschedulerapi.domain.EmailRequest;
import samuel.oliveira.silva.roomschedulerapi.service.EmailService;

/**
 * Class to read the messages from RabbitMQ.
 */
@Component
public class EmailListener {

  @Autowired EmailService emailService;

  @RabbitListener(queues = QUEUE_EMAIL)
  public void emailListener(@Payload EmailRequest request) {
    emailService.sendEmail(request.getEvent(), request.getDestiny());
  }
}
