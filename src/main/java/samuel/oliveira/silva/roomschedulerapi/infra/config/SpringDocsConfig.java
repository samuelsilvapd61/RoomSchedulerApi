package samuel.oliveira.silva.roomschedulerapi.infra.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Configuration for Swagger. */
@Configuration
@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class SpringDocsConfig {

  /**
   * Configuration for Swagger.
   * Setting how to use authentication and adding some of my information.
   *
   * @return OpenAPI
   */
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .components(
            new Components()
                .addSecuritySchemes(
                    "bearer-key",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")))
        .info(
            new Info()
                .title("RoomSchedulerAPI")
                .description("RoomSchedulerAPIext - Contains CRUD of User, Room and Schedule.")
                .contact(
                    new Contact()
                        .name("Samuel de Oliveira Silva")
                        .url("https://www.youtube.com/@DevSamuquinha")
                        .email("samuelsilvapd61@gmail.com")));
  }
}
