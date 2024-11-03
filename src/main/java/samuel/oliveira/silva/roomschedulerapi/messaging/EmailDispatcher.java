package samuel.oliveira.silva.roomschedulerapi.messaging;

import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.RabbitMq.QUEUE_EMAIL;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import samuel.oliveira.silva.roomschedulerapi.domain.request.EmailRequest;

/**
 * Class to send messages to RabbitMQ.
 */
@Component
public class EmailDispatcher {

  @Autowired RabbitTemplate rabbitTemplate;

  public void sendEmailToRabbitMq(EmailRequest request) {
    rabbitTemplate.convertAndSend(QUEUE_EMAIL, request);
  }
}
