package samuel.oliveira.silva.roomschedulerapi.utils;

/** Class to store constant values. */
public class Constants {

  /**
   * Constants related to endpoint paths.
   */
  public static class Path {
    public static final String PATH_ID = "/{id}";
    public static final String PATH_USER = "/user";
    public static final String PATH_ROOM = "/room";
    public static final String PATH_SCHEDULE = "/schedule";
    public static final String PATH_LOGIN = "/login";
    public static final String PATH_CACHE = "/cache";
  }

  /**
   * Constants related to user roles.
   */
  public static class Role {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
  }

  /**
   * Constants related to authentication.
   */
  public static class Authentication {
    public static final String AUTHENTICATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String TIME_ZONE = "-03:00";
  }

  /**
   * Constants related to cache names.
   */
  public static class Cache {
    public static final String ROOMS = "rooms";
    public static final String NEXT_ROOM_SCHEDULES = "nextRoomSchedules";
    public static final String KEY_ID = "#id";
  }

  /**
   * Constants related to rabbitmq.
   */
  public static class RabbitMq {
    public static final String QUEUE_EMAIL = "room-scheduler.email";
    public static final String DLQ_SUFIX = "-dlq";
    public static final String EMAIL_DLX = "email.dlx";
  }

  /**
   * Generic constants.
   */
  public static class Generics {
    public static final String APPLICATION_NAME = "RoomSchedulerAPI";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String SCHEDULE_DATE = "schedule_date";
    public static final String EMPTY_STRING = "";
  }

}
