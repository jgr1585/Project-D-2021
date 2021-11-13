package fhv.teamd.hotel.application.impl;

import fhv.teamd.hotel.application.RoomAssignmentService;
import fhv.teamd.hotel.application.dto.RoomDTO;
import fhv.teamd.hotel.domain.Room;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomAssignmentServiceImpl implements RoomAssignmentService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    @Transactional
    public List<RoomDTO> findSuitableRooms(String categoryId, int amount) {

        List<Room> rooms = this.roomRepository.getByCategory(new CategoryId(categoryId));

        return rooms
                .stream()
                .limit(amount)
                .map(RoomDTO::fromRoom)
                .collect(Collectors.toList());

    }
}
