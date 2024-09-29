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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import samuel.oliveira.silva.roomschedulerapi.domain.request.ScheduleIncludeRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.request.ScheduleRemoveRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.response.ScheduleResponse;
import samuel.oliveira.silva.roomschedulerapi.service.ScheduleService;

/** Controller of Schedule. */
@RestController
@RequestMapping("schedule")
public class ScheduleController {

  @Autowired private ScheduleService service;

  @PostMapping
  public void includeSchedule(@Valid @RequestBody ScheduleIncludeRequest request) {
    service.addSchedule(request);
  }

  @GetMapping("/{id}")
  public PagedModel<ScheduleResponse> listNextSchedules(
      @PathVariable Long id,
      @PageableDefault(
              size = 10,
              sort = {ID})
          Pageable pagination) {
    return service.listNextSchedules(id, pagination);
  }

  @DeleteMapping
  public void removeSchedule(@RequestBody ScheduleRemoveRequest request) {
    service.removeSchedule(request);
  }
}
