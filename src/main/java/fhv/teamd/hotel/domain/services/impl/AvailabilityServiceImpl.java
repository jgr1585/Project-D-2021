package fhv.teamd.hotel.domain.services.impl;

import fhv.teamd.hotel.domain.Room;
import fhv.teamd.hotel.domain.Stay;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.repositories.BookingRepository;
import fhv.teamd.hotel.domain.repositories.CategoryRepository;
import fhv.teamd.hotel.domain.repositories.RoomRepository;
import fhv.teamd.hotel.domain.repositories.StayRepository;
import fhv.teamd.hotel.domain.services.AvailabilityService;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private StayRepository stayRepository;

    @Override
    public int getAmountOfAvailableCategory(CategoryId categoryId, LocalDateTime from, LocalDateTime until) {

        List<Room> rooms = this.roomRepository.getByCategory(categoryId);

        int amountBookedRooms = this.bookingRepository.getNumberOfBookedRoomsByCategory(categoryId, from, until);
        int amountStayRooms = this.stayRepository.getNumberOfStayRoomsByCategory(categoryId, from, until);

        return rooms.size() - (amountBookedRooms + amountStayRooms);
    }

    @Override
    public List<Room> findSuitableRooms(CategoryId categoryId, LocalDateTime from, LocalDateTime until, int maxAmount) {

        List<Room> rooms = this.roomRepository.getByCategory(categoryId);

        List<Stay> overlappingStays = this.stayRepository
                .activeStaysWithOverlappingDuration(from, until);

        Set<Room> occupiedRooms = overlappingStays
                .stream()
                .flatMap(stay -> stay.rooms().stream())
                .collect(Collectors.toSet());

        rooms.removeAll(occupiedRooms);

        return rooms.stream().limit(maxAmount).collect(Collectors.toList());
    }

    @Override
    public boolean isAvailableCategory(Map.Entry<String, Integer> categoryIdsAndAmounts, LocalDateTime from, LocalDateTime until, int amount) {

        throw new NotYetImplementedException();
    }

    @Override
    public boolean areAvailableRooms(Set<Room> rooms, LocalDateTime from, LocalDateTime until) {

        List<Stay> overlappingStays = this.stayRepository
                .activeStaysWithOverlappingDuration(from, until);

        Set<Room> occupiedRooms = overlappingStays
                .stream()
                .flatMap(stay -> stay.rooms().stream())
                .collect(Collectors.toSet());

        for (Room room : rooms) {
            for (Room occupiedRoom : occupiedRooms) {
                if (room.equals(occupiedRoom)) {
                    return false;
                }
            }
        }

        return true;
    }
}
