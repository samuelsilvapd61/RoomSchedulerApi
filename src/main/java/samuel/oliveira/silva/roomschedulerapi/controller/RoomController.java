package samuel.oliveira.silva.roomschedulerapi.controller;

import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.Path.PATH_ID;
import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.Path.PATH_ROOM;
import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.Role.ROLE_ADMIN;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import samuel.oliveira.silva.roomschedulerapi.domain.request.RoomIncludeRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.request.RoomUpdateRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.response.RoomResponse;
import samuel.oliveira.silva.roomschedulerapi.service.RoomService;

/** Controller for Room. */
@RestController
@RequestMapping(PATH_ROOM)
@SecurityRequirement(name = "bearer-key")
public class RoomController {

  @Autowired private RoomService service;

  @Autowired private CacheManager cacheManager;

  /**
   * Adds a new user.
   *
   * @param request new room data
   * @param uriBuilder URL to get room
   * @return a new room
   */
  @PostMapping
  @Secured(ROLE_ADMIN)
  public ResponseEntity<RoomResponse> includeRoom(
      @Valid @RequestBody RoomIncludeRequest request, UriComponentsBuilder uriBuilder) {
    var room = service.addRoom(request);
    var uri = uriBuilder.path(PATH_ROOM + PATH_ID).buildAndExpand(room.id()).toUri();
    return ResponseEntity.created(uri).body(room);
  }

  @GetMapping(PATH_ID)
  public ResponseEntity<RoomResponse> getRoom(@PathVariable Long id) {
    return ResponseEntity.ok(service.getRoom(id));
  }

  @GetMapping
  public ResponseEntity<List<RoomResponse>> listRooms() {
    return ResponseEntity.ok(service.listAllRooms());
  }

  @PutMapping
  @Secured(ROLE_ADMIN)
  public ResponseEntity<RoomResponse> updateRoom(@Valid @RequestBody RoomUpdateRequest request) {
    return ResponseEntity.ok(service.updateRoom(request));
  }

  @DeleteMapping(PATH_ID)
  @Secured(ROLE_ADMIN)
  public ResponseEntity<Void> removeRoom(@PathVariable Long id) {
    service.removeRoom(id);
    return ResponseEntity.noContent().build();
  }
}
