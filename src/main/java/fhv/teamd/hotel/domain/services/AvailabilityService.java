package fhv.teamd.hotel.domain.services;


import fhv.teamd.hotel.domain.Room;
import fhv.teamd.hotel.domain.ids.CategoryId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface AvailabilityService {

    int numberOfSuitableRooms(CategoryId categoryId, LocalDateTime from, LocalDateTime until);

    Set <Room> occupiedRooms(LocalDateTime from, LocalDateTime until);

    List<Room> suitableRooms(CategoryId categoryId, LocalDateTime from, LocalDateTime until, int maxAmount);

    boolean isAvailable(CategoryId categoryId, LocalDateTime from, LocalDateTime until, int amount);

    boolean areAvailable(Set<Room> rooms, LocalDateTime from, LocalDateTime until);

}
