package samuel.oliveira.silva.roomschedulerapi.controller;

import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.PATH_ID;
import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.PATH_SCHEDULE;
import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.SCHEDULE_DATE;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import samuel.oliveira.silva.roomschedulerapi.domain.request.ScheduleIncludeRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.request.ScheduleRemoveRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.response.ScheduleResponse;
import samuel.oliveira.silva.roomschedulerapi.service.ScheduleService;

/** Controller of Schedule. */
@RestController
@RequestMapping(PATH_SCHEDULE)
public class ScheduleController {

  @Autowired private ScheduleService service;

  /**
   * Adds a new schedule.
   *
   * @param request new schedule data
   * @return a new schedule
   */
  @PostMapping
  public ResponseEntity<ScheduleResponse> includeSchedule(
      @Valid @RequestBody ScheduleIncludeRequest request, UriComponentsBuilder uriBuilder) {
    var schedule = service.addSchedule(request);
    var uri =
        uriBuilder
            .path(PATH_SCHEDULE + PATH_ID)
            .buildAndExpand(request.userId().toString())
            .toUri();
    return ResponseEntity.created(uri).body(schedule);
  }

  @GetMapping(PATH_ID)
  public ResponseEntity<PagedModel<ScheduleResponse>> listNextSchedules(
      @PathVariable Long id,
      @PageableDefault(
              size = 10,
              sort = {SCHEDULE_DATE})
          Pageable pagination) {
    return ResponseEntity.ok(service.listNextSchedules(id, pagination));
  }

  @DeleteMapping
  public ResponseEntity<Void> removeSchedule(@Valid @RequestBody ScheduleRemoveRequest request) {
    service.removeSchedule(request);
    return ResponseEntity.noContent().build();
  }
}
