package samuel.oliveira.silva.roomschedulerapi.service.impl;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import samuel.oliveira.silva.roomschedulerapi.domain.Schedule;
import samuel.oliveira.silva.roomschedulerapi.domain.ScheduleId;
import samuel.oliveira.silva.roomschedulerapi.domain.request.ScheduleIncludeRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.request.ScheduleRemoveRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.response.ScheduleResponse;
import samuel.oliveira.silva.roomschedulerapi.repository.ScheduleRepository;
import samuel.oliveira.silva.roomschedulerapi.service.EntityActionExecutor;
import samuel.oliveira.silva.roomschedulerapi.service.RoomService;
import samuel.oliveira.silva.roomschedulerapi.service.ScheduleService;
import samuel.oliveira.silva.roomschedulerapi.service.UserService;

/** Business and logical actions of schedule. */
@Service
public class ScheduleServiceImpl implements ScheduleService, EntityActionExecutor {

  @Autowired ScheduleRepository scheduleRepository;

  @Autowired UserService userService;

  @Autowired RoomService roomService;

  @Override
  public ScheduleResponse addSchedule(ScheduleIncludeRequest request) {
    var userId = request.userId();
    var roomId = request.roomId();
    var scheduleDate = LocalDate.parse(request.scheduleDate());
    userService.userExistsOrThrowException(userId);
    roomService.roomExistsOrThrowException(roomId);

    if (scheduleDateIsNotAtLeastToday(scheduleDate)) {
      throw new RuntimeException(
          "You must schedule for a day that is today or a day in the future.");
    }
    if (scheduleAlreadyExists(roomId, scheduleDate)) {
      throw new RuntimeException("This schedule already exists.");
    }

    var newSchedule = new Schedule(request);
    return new ScheduleResponse(scheduleRepository.save(newSchedule));
  }

  @Override
  public PagedModel<ScheduleResponse> listNextSchedules(Long id, Pageable pagination) {
    return new PagedModel<ScheduleResponse>(
        scheduleRepository
            .findNextSchedulesByUserId(id, LocalDate.now(), pagination)
            .map(ScheduleResponse::new));
  }

  @Override
  public void removeSchedule(ScheduleRemoveRequest request) {
    var scheduleId = new ScheduleId(request);
    executeActionIfEntityExists(
        scheduleId,
        scheduleRepository,
        schedule -> {
          scheduleRepository.deleteById(scheduleId);
          return null;
        },
        "This schedule does not exist.");
  }

  private boolean scheduleAlreadyExists(Long roomId, LocalDate scheduleDate) {
    var existingSchedule =
        scheduleRepository.findById(
            ScheduleId.builder().room(roomId).scheduleDate(scheduleDate).build());
    return existingSchedule.isPresent();
  }

  private boolean scheduleDateIsNotAtLeastToday(LocalDate scheduleDate) {
    return scheduleDate.isBefore(LocalDate.now());
  }
}
