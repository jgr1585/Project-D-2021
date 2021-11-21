package fhv.teamd.hotel.application;

import fhv.teamd.hotel.application.dto.RoomDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomSuggestionService {

    List<RoomDTO> findSuitableRooms(String categoryId, LocalDateTime from, LocalDateTime until, int maxAmount);

}
