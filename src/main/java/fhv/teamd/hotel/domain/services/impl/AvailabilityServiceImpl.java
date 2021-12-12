package fhv.teamd.hotel.domain.services.impl;

import fhv.teamd.hotel.domain.Room;
import fhv.teamd.hotel.domain.Stay;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.repositories.BookingRepository;
import fhv.teamd.hotel.domain.repositories.RoomRepository;
import fhv.teamd.hotel.domain.repositories.StayRepository;
import fhv.teamd.hotel.domain.services.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private StayRepository stayRepository;

    @Override
    public int numberOfSuitableRooms(CategoryId categoryId, LocalDateTime from, LocalDateTime until) {

        List<Room> rooms = this.roomRepository.getByCategory(categoryId);

        int amountBookedRooms = this.bookingRepository.numberOfBookedRoomsByCategory(categoryId, from, until);
        int amountStayRooms = this.stayRepository.getNumberOfStayRoomsByCategory(categoryId, from, until);

        return rooms.size() - (amountBookedRooms + amountStayRooms);
    }

    @Override
    public Set<Room> occupiedRooms(LocalDateTime from, LocalDateTime until) {
        return this.stayRepository
                .activeStaysWithOverlappingDuration(from, until)
                .stream()
                .flatMap(stay -> stay.rooms().stream())
                .collect(Collectors.toSet());
    }

    @Override
    public List<Room> suitableRooms(CategoryId categoryId, LocalDateTime from, LocalDateTime until, int maxAmount) {

        List<Room> rooms = this.roomRepository.getByCategory(categoryId);

        rooms.removeAll(this.occupiedRooms(from, until));

        return rooms.stream().limit(maxAmount).collect(Collectors.toList());
    }

    @Override
    public boolean isAvailable(CategoryId categoryId, LocalDateTime from, LocalDateTime until, int amount) {

        return this.numberOfSuitableRooms(categoryId, from, until) >= amount;
    }

    @Override
    public boolean areAvailable(Set<Room> rooms, LocalDateTime from, LocalDateTime until) {

        Set<Room> occupiedRooms = this.occupiedRooms(from, until);

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
