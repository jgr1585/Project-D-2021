package fhv.teamd.hotel.domain.services;


import fhv.teamd.hotel.domain.Room;
import fhv.teamd.hotel.domain.ids.CategoryId;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomAssignmentService {

    List<Room> findSuitableRooms(CategoryId categoryId, LocalDateTime from, LocalDateTime until, int maxAmount);

    boolean areAvailable(List<Room> rooms, LocalDateTime from, LocalDateTime until);

}
