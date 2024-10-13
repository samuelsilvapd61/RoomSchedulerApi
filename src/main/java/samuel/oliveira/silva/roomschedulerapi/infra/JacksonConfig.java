package samuel.oliveira.silva.roomschedulerapi.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
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
   * to be able to return a correct response when an exception is thrown in the authentication
   * process. Also, it's adding a configuration that ignores received fields that are not mapped in
   * the endpoints.
   * The @Primary annotation ensures that this ObjectMapper is the main in the application.
   *
   * @return ObjectMapper
   */
  @Bean
  @Primary
  public ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return mapper;
  }
}
