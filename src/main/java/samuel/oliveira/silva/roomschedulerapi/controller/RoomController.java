package samuel.oliveira.silva.roomschedulerapi.controller;

import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.EMPTY_STRING;
import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.ID;
import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.NAME;
import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.PATH_ID;
import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.PATH_ROOM;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import samuel.oliveira.silva.roomschedulerapi.domain.request.RoomIncludeRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.request.RoomUpdateRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.response.RoomResponse;
import samuel.oliveira.silva.roomschedulerapi.service.RoomService;
import samuel.oliveira.silva.roomschedulerapi.utils.Constants;

/** Controller for Room. */
@RestController
@RequestMapping(PATH_ROOM)
@SecurityRequirement(name = "bearer-key")
public class RoomController {

  @Autowired private RoomService service;

  /**
   * Adds a new user.
   *
   * @param request new room data
   * @param uriBuilder URL to get room
   * @return a new room
   */
  @PostMapping
  @Secured(Constants.ROLE_ADMIN)
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
  public ResponseEntity<PagedModel<RoomResponse>> listRooms(
      @RequestParam(value = NAME, defaultValue = EMPTY_STRING) String name,
      @PageableDefault(size = 10, sort = {ID}) Pageable pagination) {
    return ResponseEntity.ok(service.listRooms(name, pagination));
  }

  @PutMapping
  @Secured(Constants.ROLE_ADMIN)
  public ResponseEntity<RoomResponse> updateRoom(@Valid @RequestBody RoomUpdateRequest request) {
    return ResponseEntity.ok(service.updateRoom(request));
  }

  @DeleteMapping(PATH_ID)
  @Secured(Constants.ROLE_ADMIN)
  public ResponseEntity<Void> removeRoom(@PathVariable Long id) {
    service.removeRoom(id);
    return ResponseEntity.noContent().build();
  }
}
