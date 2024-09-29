package samuel.oliveira.silva.roomschedulerapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samuel.oliveira.silva.roomschedulerapi.domain.Room;
import samuel.oliveira.silva.roomschedulerapi.domain.request.RoomIncludeRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.request.RoomUpdateRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.response.RoomResponse;
import samuel.oliveira.silva.roomschedulerapi.repository.RoomRepository;
import samuel.oliveira.silva.roomschedulerapi.service.EntityActionExecutor;
import samuel.oliveira.silva.roomschedulerapi.service.RoomService;

/**
 * Business and logical actions of room.
 */
@Service
public class RoomServiceImpl implements RoomService, EntityActionExecutor {

  @Autowired
  RoomRepository repository;

  @Override
  @Transactional
  public void addRoom(RoomIncludeRequest request) {
    var newRoom = new Room(request);
    repository.save(newRoom);
  }

  @Override
  public PagedModel<RoomResponse> listRooms(Pageable pagination) {
    return new PagedModel<>(repository.findAll(pagination).map(RoomResponse::new));
  }

  @Override
  @Transactional
  public void updateRoom(RoomUpdateRequest request) {
    executeActionIfEntityExists(
        request.id(),
        repository,
        room -> room.updateRoom(request),
        "This room does not exist."
    );
  }

  @Override
  @Transactional
  public void removeRoom(Long id) {
    executeActionIfEntityExists(
        id,
        repository,
        room -> repository.deleteById(room.getId()),
        "This room does not exist."
    );
  }

  /**
   * Verifies if the room exists, if not, throws an exception.
   *
   * @param id id of room
   */
  public void roomExistsOrThrowException(Long id) {
    executeActionIfEntityExists(
        id,
        repository,
        room -> {},
        "This room does not exist."
    );
  }

}
