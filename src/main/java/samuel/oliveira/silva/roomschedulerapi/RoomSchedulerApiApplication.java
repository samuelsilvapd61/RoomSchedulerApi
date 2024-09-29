package samuel.oliveira.silva.roomschedulerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Main class. It runs the application.
 */
@SpringBootApplication
public class RoomSchedulerApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(RoomSchedulerApiApplication.class, args);
  }

}
