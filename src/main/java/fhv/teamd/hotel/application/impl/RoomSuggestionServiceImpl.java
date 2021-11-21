package fhv.teamd.hotel.application.impl;

import fhv.teamd.hotel.application.RoomSuggestionService;
import fhv.teamd.hotel.application.dto.RoomDTO;
import fhv.teamd.hotel.domain.ids.CategoryId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomSuggestionServiceImpl implements RoomSuggestionService {

    @Autowired
    private fhv.teamd.hotel.domain.services.RoomAssignmentService roomAssignmentService;

    @Override
    @Transactional(readOnly = true)
    public List<RoomDTO> findSuitableRooms(String categoryId, LocalDateTime from, LocalDateTime until, int maxAmount) {

        return this.roomAssignmentService
                .findSuitableRooms(new CategoryId(categoryId), from, until, maxAmount)
                .stream()
                .map(RoomDTO::fromRoom)
                .collect(Collectors.toList());

    }
}
