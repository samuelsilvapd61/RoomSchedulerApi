package samuel.oliveira.silva.roomschedulerapi.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static samuel.oliveira.silva.roomschedulerapi.utils.Constants.Path.PATH_ROOM;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import samuel.oliveira.silva.roomschedulerapi.domain.Room;
import samuel.oliveira.silva.roomschedulerapi.domain.request.RoomIncludeRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.request.RoomUpdateRequest;
import samuel.oliveira.silva.roomschedulerapi.domain.response.RoomResponse;
import samuel.oliveira.silva.roomschedulerapi.service.RoomService;

class RoomControllerTest extends AbstractTestController {

  @InjectMocks RoomController roomController;

  @Mock private RoomService service;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    super.setupMvc(roomController);
  }

  @Test
  void given_validRequest_when_includeRoom_then_callRoomServiceAndReturnNewRoom() throws Exception {
    // Arrange
    var roomRequest = new RoomIncludeRequest("Room name");
    var jsonBody = toJson(roomRequest);
    var requestBuilder =
        MockMvcRequestBuilders.post(PATH_ROOM)
            .content(jsonBody)
            .characterEncoding(StandardCharsets.UTF_8)
            .contentType(MediaType.APPLICATION_JSON);


    var roomResponse = new RoomResponse(Room.builder().id(1L).build());
    when(service.addRoom(Mockito.any(RoomIncludeRequest.class))).thenReturn(roomResponse);

    // Act
    var mvcResult = mvc.perform(requestBuilder).andReturn();
    roomResponse = toObject(mvcResult.getResponse().getContentAsString(), RoomResponse.class);

    // Assert
    verify(service, Mockito.only()).addRoom(Mockito.any(RoomIncludeRequest.class));
    assertNotNull(roomResponse);
  }

  @Test
  void given_validRequest_when_getRoom_then_returnRoom() throws Exception {
    // Arrange
    var requestBuilder =
        MockMvcRequestBuilders.get(PATH_ROOM + "/1")
            .characterEncoding(StandardCharsets.UTF_8)
            .contentType(MediaType.APPLICATION_JSON);

    when(service.getRoom(1L)).thenReturn(new RoomResponse(1L, "", LocalDateTime.now(), LocalDateTime.now()));

    // Act
    var mvcResult = mvc.perform(requestBuilder).andReturn();
    var roomResponse = toObject(mvcResult.getResponse().getContentAsString(), RoomResponse.class);

    // Assert
    verify(service, Mockito.only()).getRoom(Mockito.any(Long.class));
    assertNotNull(roomResponse);
  }

  @Test
  void given_validRequest_when_listRooms_then_returnRooms() throws Exception {
    // Arrange
    var requestBuilder =
        MockMvcRequestBuilders.get(PATH_ROOM)
            .characterEncoding(StandardCharsets.UTF_8)
            .contentType(MediaType.APPLICATION_JSON);

    var roomList = List.of(new RoomResponse(1L, "", LocalDateTime.now(), LocalDateTime.now()));
    when(service.listAllRooms()).thenReturn(roomList);

    // Act
    var mvcResult = mvc.perform(requestBuilder).andReturn();
    var roomResponse = toObject(mvcResult.getResponse().getContentAsString(), List.class);

    // Assert
    verify(service, Mockito.only()).listAllRooms();

    assertTrue(ArrayUtils.isNotEmpty(roomResponse.toArray()));
  }

  @Test
  void given_validRequest_when_updateRoom_then_returnUpdatedRoom() throws Exception {
    // Arrange
    var roomUpdateRequest = new RoomUpdateRequest(1L, "Room name");
    var jsonBody = toJson(roomUpdateRequest);
    var requestBuilder =
        MockMvcRequestBuilders.put(PATH_ROOM)
            .content(jsonBody)
            .characterEncoding(StandardCharsets.UTF_8)
            .contentType(MediaType.APPLICATION_JSON);

    var roomResponse = new RoomResponse(1L, null, null, null);
    when(service.updateRoom(roomUpdateRequest)).thenReturn(roomResponse);

    // Act
    var mvcResult = mvc.perform(requestBuilder).andReturn();
    roomResponse = toObject(mvcResult.getResponse().getContentAsString(), RoomResponse.class);

    // Assert
    verify(service, Mockito.only()).updateRoom(roomUpdateRequest);
    assertNotNull(roomResponse);
  }

  @Test
  void given_validRequest_when_removeRoom_then_success() throws Exception {
    // Arrange
    var requestBuilder =
        MockMvcRequestBuilders.delete(PATH_ROOM + "/1")
            .characterEncoding(StandardCharsets.UTF_8)
            .contentType(MediaType.APPLICATION_JSON);

    // Act
    var mvcResult = mvc.perform(requestBuilder).andReturn();
    var roomResponse = mvcResult.getResponse().getContentAsString();

    // Assert
    verify(service, Mockito.only()).removeRoom(1L);
    Assertions.assertTrue(StringUtils.isBlank(roomResponse));
  }

}
