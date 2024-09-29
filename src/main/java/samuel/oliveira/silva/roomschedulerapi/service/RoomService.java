package samuel.oliveira.silva.roomschedulerapi.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import samuel.oliveira.silva.roomschedulerapi.domain.request.RoomIncludeRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.request.RoomUpdateRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.response.RoomResponse;

/**
 * Interface service.
 */
public interface RoomService {

  void addRoom(RoomIncludeRequest request);

  PagedModel<RoomResponse> listRooms(Pageable pagination);

  void updateRoom(RoomUpdateRequest request);

  void removeRoom(Long id);

  void roomExistsOrThrowException(Long id);

}
