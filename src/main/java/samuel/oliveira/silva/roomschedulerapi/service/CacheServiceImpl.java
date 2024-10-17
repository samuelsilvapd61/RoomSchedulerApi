package samuel.oliveira.silva.roomschedulerapi.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import samuel.oliveira.silva.roomschedulerapi.domain.response.RoomResponse;
import samuel.oliveira.silva.roomschedulerapi.domain.response.RoomSchedulesResponse;
import samuel.oliveira.silva.roomschedulerapi.repository.RoomRepository;
import samuel.oliveira.silva.roomschedulerapi.repository.ScheduleRepository;

/**
 * Service to manipulate cache. Cache is data stored in memory.
 * In this system, it's being used the Redis database as a cache.
 */
@Service
public class CacheServiceImpl {

  @Autowired CacheManager cacheManager;
  @Autowired RoomRepository roomRepository;
  @Autowired ScheduleRepository scheduleRepository;

  /**
   * This method clears all the cache created.
   */
  public void evictAllCache() {
    var cacheNameList = Objects.requireNonNull(cacheManager.getCacheNames());
    cacheNameList.stream()
        .map(cacheName -> cacheManager.getCache(cacheName))
        .filter(Objects::nonNull)
        .forEach(Cache::clear);
  }

  @CachePut("rooms")
  public List<RoomResponse> updateRoomsCache() {
    return roomRepository.findAll().stream().map(RoomResponse::new).toList();
  }

  @CacheEvict(value = "nextRoomSchedules", allEntries = true)
  @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
  public void clearRoomsSchedulesCache() {}

  /**
   * This method updates the cache in memory
   * for the response of ScheduleService.listNextRoomSchedules().
   *
   * @param id roomId
   * @param date date
   * @return the response that would be returned by the database
   */
  @CachePut(value = "nextRoomSchedules", key = "#id")
  public RoomSchedulesResponse updateNextRoomSchedules(Long id, LocalDate date) {
    var dates = scheduleRepository.findNextSchedulesByRoomId(id, date);
    var localDates = dates.stream().map(Date::toLocalDate).toList();
    return new RoomSchedulesResponse(id, localDates);
  }
}
