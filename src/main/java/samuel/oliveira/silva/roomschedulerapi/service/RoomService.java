package samuel.oliveira.silva.roomschedulerapi.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import samuel.oliveira.silva.roomschedulerapi.domain.request.RoomIncludeRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.request.RoomUpdateRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.response.RoomResponse;

/** Interface service. */
public interface RoomService {

  RoomResponse addRoom(RoomIncludeRequest request);

  RoomResponse getRoom(Long id);

  PagedModel<RoomResponse> listRooms(Pageable pagination);

  RoomResponse updateRoom(RoomUpdateRequest request);

  void removeRoom(Long id);

  void roomExistsOrThrowException(Long id);
}
