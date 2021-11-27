package fhv.teamd.hotel.domain.services;


import fhv.teamd.hotel.domain.Room;
import fhv.teamd.hotel.domain.ids.CategoryId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AvailabilityService {

    int getAmountOfAvailableCategory(CategoryId categoryId, LocalDateTime from, LocalDateTime until);

    List<Room> findSuitableRooms(CategoryId categoryId, LocalDateTime from, LocalDateTime until, int maxAmount);

    boolean isAvailableCategory(Map.Entry<String, Integer> categoryIdsAndAmounts, LocalDateTime from, LocalDateTime until, int amount);

    boolean areAvailableRooms(Set<Room> rooms, LocalDateTime from, LocalDateTime until);


}
