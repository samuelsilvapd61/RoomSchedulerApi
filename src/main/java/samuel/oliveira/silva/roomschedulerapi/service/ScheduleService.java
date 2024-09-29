package samuel.oliveira.silva.roomschedulerapi.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import samuel.oliveira.silva.roomschedulerapi.domain.request.ScheduleIncludeRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.request.ScheduleRemoveRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.response.ScheduleResponse;

/**
 * Interface service.
 */
public interface ScheduleService {

  void addSchedule(ScheduleIncludeRequest request);

  PagedModel<ScheduleResponse> listNextSchedules(Long id, Pageable pagination);

  void removeSchedule(ScheduleRemoveRequest request);

}
