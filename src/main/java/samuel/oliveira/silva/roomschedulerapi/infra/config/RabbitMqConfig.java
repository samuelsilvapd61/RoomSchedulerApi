package samuel.oliveira.silva.roomschedulerapi.infra.config;

import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.RabbitMq.QUEUE_EMAIL;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class configures the queue creation and the object-to-json converting.
 */
@Configuration
public class RabbitMqConfig {

  @Bean
  public Queue createQueue() {
    return QueueBuilder.durable(QUEUE_EMAIL).build();
  }

  @Bean
  public RabbitAdmin crateRabbitAdmin(ConnectionFactory connectionFactory) {
    return new RabbitAdmin(connectionFactory);
  }

  @Bean
  public ApplicationListener<ApplicationReadyEvent> initializeAdmin(RabbitAdmin rabbitAdmin) {
    return event -> rabbitAdmin.initialize();
  }

  @Bean
  public Jackson2JsonMessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  /**
   * This method configures the connection to RabbitMQ and the object conversion.
   *
   * @param connectionFactory connection with RabbitMQ
   * @param messageConverter object-to-json and json-to-object conversion
   * @return rabbitTemplate
   */
  @Bean
  public RabbitTemplate rabbitTemplate(
      ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(messageConverter);
    return rabbitTemplate;
  }

}
