package samuel.oliveira.silva.roomschedulerapi.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/** This class adds configuration to ObjectMapper. */
@Configuration
public class JacksonConfig {

  /**
   * This method teaches ObjectMapper how to deserialize a LocalDateTime value. This was necessary,
   * to be able to return a correct response when an exception is thrown
   * in the authentication process.
   *
   * @return ObjectMapper
   */
  @Bean
  @Primary // This annotation ensures that this ObjectMapper is the main in the application.
  public ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    return mapper;
  }
}
