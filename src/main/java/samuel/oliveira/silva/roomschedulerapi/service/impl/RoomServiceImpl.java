package samuel.oliveira.silva.roomschedulerapi.service.impl;

import static samuel.oliveira.silva.roomschedulerapi.messaging.EmailEvent.REMOVED_ROOM;
import static samuel.oliveira.silva.roomschedulerapi.messaging.EmailEvent.UPDATED_ROOM;
import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.Cache.ROOMS;

import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samuel.oliveira.silva.roomschedulerapi.messaging.EmailEvent;
import samuel.oliveira.silva.roomschedulerapi.domain.request.EmailRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.Room;
import samuel.oliveira.silva.roomschedulerapi.domain.request.RoomIncludeRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.request.RoomUpdateRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.response.RoomResponse;
import samuel.oliveira.silva.roomschedulerapi.infra.exception.ApiErrorEnum;
import samuel.oliveira.silva.roomschedulerapi.infra.exception.ApiException;
import samuel.oliveira.silva.roomschedulerapi.messaging.EmailDispatcher;
import samuel.oliveira.silva.roomschedulerapi.repository.RoomRepository;
import samuel.oliveira.silva.roomschedulerapi.repository.ScheduleRepository;
import samuel.oliveira.silva.roomschedulerapi.service.EntityActionExecutor;
import samuel.oliveira.silva.roomschedulerapi.service.RoomService;

/** Business and logical actions of room. */
@Slf4j
@Service
public class RoomServiceImpl implements RoomService, EntityActionExecutor {

  @Autowired RoomRepository repository;
  @Autowired CacheServiceImpl cacheService;
  @Autowired ScheduleRepository scheduleRepository;
  @Autowired EmailDispatcher emailDispatcher;

  @Override
  @Transactional
  public RoomResponse addRoom(RoomIncludeRequest request) {
    var room = new Room(request);
    var newRoom = repository.save(room);
    cacheService.updateRoomsCache();
    log.info("Room id '{}' name '{}' created.", newRoom.getId(), newRoom.getName());
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
  @Cacheable(ROOMS)
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
              log.info(
                  "Room id '{}' had the name '{}' updated to '{}'.",
                  room.getId(), room.getName(), request.name());
              room.updateRoom(request);
              return repository.save(room);
            },
            new ApiException(ApiErrorEnum.ROOM_DOESNT_EXIST));
    cacheService.updateRoomsCache();
    sendEmailToAffectedUsers(request.id(), UPDATED_ROOM);

    return new RoomResponse(newRoom);
  }

  @Override
  @Transactional
  public void removeRoom(Long id) {
    executeActionIfEntityExists(
        id,
        repository,
        room -> {
          log.info("Room id '{}' name '{}' was removed.", room.getId(), room.getName());
          sendEmailToAffectedUsers(room.getId(), REMOVED_ROOM);
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

  private void sendEmailToAffectedUsers(Long roomId, EmailEvent event) {
    var emailList =
        scheduleRepository.listUsersWithExistingSchedulesForThisRoom(roomId, LocalDate.now());
    var emailRequestList =
        emailList.stream()
            .map(email -> EmailRequest.builder().event(event).destiny(email).build())
            .toList();
    emailRequestList.forEach(emailRequest -> emailDispatcher.sendEmailToRabbitMq(emailRequest));
  }
}
