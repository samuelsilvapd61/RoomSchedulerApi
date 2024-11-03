package samuel.oliveira.silva.roomschedulerapi.service.impl;

import static samuel.oliveira.silva.roomschedulerapi.messaging.EmailEvent.REMOVED_SCHEDULE;
import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.Cache.KEY_ID;
import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.Cache.NEXT_ROOM_SCHEDULES;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samuel.oliveira.silva.roomschedulerapi.domain.request.EmailRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.Schedule;
import samuel.oliveira.silva.roomschedulerapi.domain.ScheduleId;
import samuel.oliveira.silva.roomschedulerapi.domain.request.ScheduleIncludeRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.request.ScheduleRemoveRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.response.RoomSchedulesResponse;
import samuel.oliveira.silva.roomschedulerapi.domain.response.ScheduleResponse;
import samuel.oliveira.silva.roomschedulerapi.infra.exception.ApiErrorEnum;
import samuel.oliveira.silva.roomschedulerapi.infra.exception.ApiException;
import samuel.oliveira.silva.roomschedulerapi.messaging.EmailDispatcher;
import samuel.oliveira.silva.roomschedulerapi.repository.ScheduleRepository;
import samuel.oliveira.silva.roomschedulerapi.service.EntityActionExecutor;
import samuel.oliveira.silva.roomschedulerapi.service.RoomService;
import samuel.oliveira.silva.roomschedulerapi.service.ScheduleService;
import samuel.oliveira.silva.roomschedulerapi.service.UserService;

/** Business and logical actions of schedule. */
@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService, EntityActionExecutor {

  @Autowired ScheduleRepository scheduleRepository;
  @Autowired UserService userService;
  @Autowired RoomService roomService;
  @Autowired CacheServiceImpl cacheService;
  @Autowired EmailDispatcher emailDispatcher;

  @Override
  @Transactional
  public ScheduleResponse addSchedule(ScheduleIncludeRequest request) {
    var userId = request.userId();
    var roomId = request.roomId();
    var scheduleDate = LocalDate.parse(request.scheduleDate());
    validateSchedule(userId, roomId, scheduleDate);
    var newScheduleEntity = new Schedule(request);
    var newSavedSchedule = scheduleRepository.save(newScheduleEntity);

    cacheService.updateNextRoomSchedules(roomId, LocalDate.now());
    return new ScheduleResponse(newSavedSchedule);
  }

  @Override
  public PagedModel<ScheduleResponse> listNextUserSchedules(Long id, Pageable pagination) {
    userService.getUserExistsOrThrowException(id);
    return new PagedModel<ScheduleResponse>(
        scheduleRepository
            .findNextSchedulesByUserId(id, LocalDate.now(), pagination)
            .map(ScheduleResponse::new));
  }

  @Override
  @Cacheable(value = NEXT_ROOM_SCHEDULES, key = KEY_ID)
  public RoomSchedulesResponse listNextRoomSchedules(Long id) {
    roomService.roomExistsOrThrowException(id);
    List<Date> dates = scheduleRepository.findNextSchedulesByRoomId(id, LocalDate.now());
    var localDates = dates.stream().map(Date::toLocalDate).toList();

    return new RoomSchedulesResponse(id, localDates);
  }

  @Override
  @Transactional
  public void removeSchedule(ScheduleRemoveRequest request) {
    var user = userService.getUserExistsOrThrowException(request.userId());
    var scheduleId = new ScheduleId(request);

    var scheduleExists =
        scheduleRepository.existsByUserIdAndRoomIdAndScheduleDate(
            request.userId(), scheduleId.getRoom(), scheduleId.getScheduleDate());

    if (scheduleExists) {
      scheduleRepository.deleteById(scheduleId);
      emailDispatcher.sendEmailToRabbitMq(
          EmailRequest.builder().event(REMOVED_SCHEDULE).destiny(user.getEmail()).build());
      cacheService.updateNextRoomSchedules(scheduleId.getRoom(), LocalDate.now());
      log.info("Schedule {} of user '{}' was removed.", scheduleId, user.getEmail());
    } else {
      throw new ApiException(ApiErrorEnum.SCHEDULE_DOESNT_EXIST);
    }
  }

  private void validateSchedule(Long userId, Long roomId, LocalDate scheduleDate) {
    userService.getUserExistsOrThrowException(userId);
    roomService.roomExistsOrThrowException(roomId);

    if (scheduleDateIsNotBetweenTodayAnd3MonthsLater(scheduleDate)) {
      throw new ApiException(ApiErrorEnum.INVALID_DATE);
    }
    if (scheduleAlreadyExists(roomId, scheduleDate)) {
      throw new ApiException(ApiErrorEnum.SCHEDULE_EXISTS);
    }
  }

  private boolean scheduleAlreadyExists(Long roomId, LocalDate scheduleDate) {
    var existingSchedule =
        scheduleRepository.findById(
            ScheduleId.builder().room(roomId).scheduleDate(scheduleDate).build());
    return existingSchedule.isPresent();
  }

  private boolean scheduleDateIsNotBetweenTodayAnd3MonthsLater(LocalDate scheduleDate) {
    return scheduleDate.isBefore(LocalDate.now())
        || scheduleDate.isAfter(LocalDate.now().plusDays(90));
  }
}
