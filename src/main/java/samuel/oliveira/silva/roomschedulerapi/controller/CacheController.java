package samuel.oliveira.silva.roomschedulerapi.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import samuel.oliveira.silva.roomschedulerapi.service.CacheServiceImpl;
import samuel.oliveira.silva.roomschedulerapi.utils.Constants;

/**
 * Controller to Cache.
 */
@RestController
@RequestMapping("/cache")
@Secured(Constants.ROLE_ADMIN)
@SecurityRequirement(name = "bearer-key")
public class CacheController {

  @Autowired CacheServiceImpl cacheService;

  @PostMapping
  public ResponseEntity<Void> clearAllCache() {
    cacheService.evictAllCache();
    return ResponseEntity.ok().build();
  }
}
