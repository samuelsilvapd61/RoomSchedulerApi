package samuel.oliveira.silva.roomschedulerapi.infra.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

/**
 * This class is a tool to be able to return personalized responses when authentication fails.
 */
@Component
public class ManualExceptionHandler {

  @Autowired private ApiExceptionHandler exceptionHandler;
  @Autowired private ObjectMapper objectMapper;

  /**
   * This method sets a response "manually", using a personalized exception.
   *
   * @param request request
   * @param response response
   * @param ex personalized exception
   * @throws IOException IOException
   */
  public void handleException(
      HttpServletRequest request, HttpServletResponse response, ApiException ex)
      throws IOException {
    WebRequest webRequest = new ServletWebRequest(request);
    var responseEntity = exceptionHandler.handleApiException(ex, webRequest);
    response.setStatus(responseEntity.getStatusCodeValue());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    objectMapper.writeValue(response.getOutputStream(), responseEntity.getBody());
  }
}
