package samuel.oliveira.silva.roomschedulerapi.service;

import java.util.List;
import samuel.oliveira.silva.roomschedulerapi.domain.request.RoomIncludeRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.request.RoomUpdateRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.response.RoomResponse;

/** Interface service. */
public interface RoomService {

  RoomResponse addRoom(RoomIncludeRequest request);

  RoomResponse getRoom(Long id);

  List<RoomResponse> listAllRooms();

  RoomResponse updateRoom(RoomUpdateRequest request);

  void removeRoom(Long id);

  void roomExistsOrThrowException(Long id);

}
