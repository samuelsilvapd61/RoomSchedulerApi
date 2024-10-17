package samuel.oliveira.silva.roomschedulerapi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samuel.oliveira.silva.roomschedulerapi.domain.Room;
import samuel.oliveira.silva.roomschedulerapi.domain.request.RoomIncludeRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.request.RoomUpdateRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.response.RoomResponse;
import samuel.oliveira.silva.roomschedulerapi.infra.exception.ApiErrorEnum;
import samuel.oliveira.silva.roomschedulerapi.infra.exception.ApiException;
import samuel.oliveira.silva.roomschedulerapi.repository.RoomRepository;
import samuel.oliveira.silva.roomschedulerapi.service.CacheServiceImpl;
import samuel.oliveira.silva.roomschedulerapi.service.EntityActionExecutor;
import samuel.oliveira.silva.roomschedulerapi.service.RoomService;

/** Business and logical actions of room. */
@Service
public class RoomServiceImpl implements RoomService, EntityActionExecutor {

  @Autowired RoomRepository repository;
  @Autowired CacheServiceImpl cacheService;

  @Override
  @Transactional
  public RoomResponse addRoom(RoomIncludeRequest request) {
    var room = new Room(request);
    var newRoom = repository.save(room);
    cacheService.updateRoomsCache();
    return new RoomResponse(newRoom);
  }

  @Override
  public RoomResponse getRoom(Long id) {
    return repository
        .findById(id)
        .map(RoomResponse::new)
        .orElseThrow(() -> new ApiException(ApiErrorEnum.ROOM_DOESNT_EXIST));
  }

  @Override
  @Cacheable("rooms")
  public List<RoomResponse> listAllRooms() {
    return repository.findAll().stream().map(RoomResponse::new).toList();
  }

  @Override
  public RoomResponse updateRoom(RoomUpdateRequest request) {
    var newRoom =
        executeActionIfEntityExists(
            request.id(),
            repository,
            room -> {
              room.updateRoom(request);
              return repository.save(room);
            },
            new ApiException(ApiErrorEnum.ROOM_DOESNT_EXIST));
    cacheService.updateRoomsCache();
    return new RoomResponse(newRoom);
  }

  @Override
  @Transactional
  public void removeRoom(Long id) {
    executeActionIfEntityExists(
        id,
        repository,
        room -> {
          repository.deleteById(room.getId());
          return null;
        },
        new ApiException(ApiErrorEnum.ROOM_DOESNT_EXIST));
    cacheService.updateRoomsCache();
  }

  /**
   * Verifies if the room exists, if not, throws an exception.
   *
   * @param id id of room
   */
  public void roomExistsOrThrowException(Long id) {
    executeActionIfEntityExists(
        id, repository, room -> null, new ApiException(ApiErrorEnum.ROOM_DOESNT_EXIST));
  }
}
