package samuel.oliveira.silva.roomschedulerapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import samuel.oliveira.silva.roomschedulerapi.infra.config.JacksonConfig;
import samuel.oliveira.silva.roomschedulerapi.infra.exception.ApiErrorDto;
import samuel.oliveira.silva.roomschedulerapi.infra.exception.ApiErrorEnum;
import samuel.oliveira.silva.roomschedulerapi.infra.exception.ApiException;
import samuel.oliveira.silva.roomschedulerapi.infra.exception.ApiExceptionHandler;

public class AbstractTestController {

  protected MockMvc mvc;

  protected ObjectMapper mapper = new JacksonConfig().objectMapper();

  protected void setupMvc(Object controller) {
    var exceptionHandler = new ApiExceptionHandler();
    this.mvc = MockMvcBuilders.standaloneSetup(controller)
        .setControllerAdvice(exceptionHandler)
        .build();
  }

  protected String toJson(Object object) {
    try {
      return mapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  protected <T> T toObject(String json, Class<T> clazz) {
    try {
      return mapper.readValue(json, clazz);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  protected <T> T toObject(String json, TypeReference<T> typeReference) {
    try {
      return mapper.readValue(json, typeReference);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  protected void assertError(ApiErrorEnum expectedError, ApiErrorDto error) {
    assertEquals(expectedError.getTitle(), error.getTitle());
    assertEquals(expectedError.getStatus().value(), error.getStatus());
    assertEquals(expectedError.getFormattedMessage(), error.getDetail());
    assertNotNull(error.getTimestamp());
  }

  protected void assertError(ApiErrorEnum expectedError, ApiException error) {
    assertEquals(expectedError.getTitle(), error.getTitle());
    assertEquals(expectedError.getStatus().value(), error.getHttpStatus().value());
    assertEquals(expectedError.getFormattedMessage(), error.getMessage());
  }

}
