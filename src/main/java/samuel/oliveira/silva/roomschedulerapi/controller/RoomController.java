package samuel.oliveira.silva.roomschedulerapi.controller;

import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.ID;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import samuel.oliveira.silva.roomschedulerapi.domain.request.RoomIncludeRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.request.RoomUpdateRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.response.RoomResponse;
import samuel.oliveira.silva.roomschedulerapi.service.RoomService;

/**
 * Controller for Room.
 */
@RestController
@RequestMapping("room")
public class RoomController {

  @Autowired
  private RoomService service;

  @PostMapping
  public void includeRoom(@Valid @RequestBody RoomIncludeRequest request) {
    service.addRoom(request);
  }

  @GetMapping
  public PagedModel<RoomResponse> listRooms(
      @PageableDefault(size = 10, sort = {ID}) Pageable pagination) {
    return service.listRooms(pagination);
  }

  @PutMapping
  public void updateRoom(@Valid @RequestBody RoomUpdateRequest request) {
    service.updateRoom(request);
  }

  @DeleteMapping("/{id}")
  public void removeRoom(@PathVariable Long id) {
    service.removeRoom(id);
  }

}
