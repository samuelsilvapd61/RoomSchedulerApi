package samuel.oliveira.silva.roomschedulerapi.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import samuel.oliveira.silva.roomschedulerapi.domain.request.ScheduleIncludeRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.request.ScheduleRemoveRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.response.RoomSchedulesResponse;
import samuel.oliveira.silva.roomschedulerapi.domain.response.ScheduleResponse;

/**
 * Interface service.
 */
public interface ScheduleService {

  ScheduleResponse addSchedule(ScheduleIncludeRequest request);

  PagedModel<ScheduleResponse> listNextUserSchedules(Long id, Pageable pagination);

  RoomSchedulesResponse listNextRoomSchedules(Long id);

  void removeSchedule(ScheduleRemoveRequest request);


}
