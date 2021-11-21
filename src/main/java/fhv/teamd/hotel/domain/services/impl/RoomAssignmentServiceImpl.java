package fhv.teamd.hotel.domain.services.impl;

import fhv.teamd.hotel.domain.Room;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.repositories.RoomRepository;
import fhv.teamd.hotel.domain.repositories.StayRepository;
import fhv.teamd.hotel.domain.services.RoomAssignmentService;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoomAssignmentServiceImpl implements RoomAssignmentService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private StayRepository stayRepository;

    @Override
    public List<Room> findSuitableRooms(CategoryId categoryId, LocalDateTime from, LocalDateTime until, int maxAmount) {

        List<Room> rooms = this.roomRepository.getByCategory(categoryId);

        Set<Room> occupiedRooms = this.stayRepository
                .staysInTimeFrameInclusive(from, until)
                .stream()
                .flatMap(stay -> stay.rooms().stream())
                .collect(Collectors.toSet());

        rooms.removeAll(occupiedRooms);

        return rooms.stream().limit(maxAmount).collect(Collectors.toList());

    }

    @Override
    public boolean areAvailable(List<Room> rooms, LocalDateTime from, LocalDateTime until) {

        throw new NotYetImplementedException();

    }
}
