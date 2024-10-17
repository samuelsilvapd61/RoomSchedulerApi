package samuel.oliveira.silva.roomschedulerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/** Main class. It runs the application. */
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class RoomSchedulerApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(RoomSchedulerApiApplication.class, args);
  }
}
