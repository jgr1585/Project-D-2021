package fhv.teamd.hotel.application;

import fhv.teamd.hotel.application.dto.RoomDTO;

import java.util.List;

public interface RoomAssignmentService {

    List<RoomDTO> findSuitableRooms(String categoryId, int amount);

}
