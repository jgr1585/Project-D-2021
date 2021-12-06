package fhv.teamd.hotel.domain.services;

import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.DomainFactory;
import fhv.teamd.hotel.domain.Room;
import fhv.teamd.hotel.domain.repositories.BookingRepository;
import fhv.teamd.hotel.domain.repositories.RoomRepository;
import fhv.teamd.hotel.domain.repositories.StayRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
public class AvailabilityServiceTest {
    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private StayRepository stayRepository;

    @MockBean
    private RoomRepository roomRepository;

    @Autowired
    private AvailabilityService availabilityService;

    @Test
    void given_room_when_numberOfSuitableRooms_return_Number_of_Rooms() {
        final Category cat1 = DomainFactory.CreateCategory();
        final LocalDateTime from = LocalDateTime.now();
        final LocalDateTime until = LocalDateTime.now().plus(Period.ofDays(1));
        final List<Room> rooms = new LinkedList<>();
        rooms.add(DomainFactory.CreateRoomInCategory(cat1));
        rooms.add(DomainFactory.CreateRoomInCategory(cat1));
        rooms.add(DomainFactory.CreateRoomInCategory(cat1));
        rooms.add(DomainFactory.CreateRoomInCategory(cat1));
        rooms.add(DomainFactory.CreateRoomInCategory(cat1));
        rooms.add(DomainFactory.CreateRoomInCategory(cat1));


        Mockito.when(this.roomRepository.getByCategory(cat1.categoryId())).thenReturn(rooms);
        Mockito.when(this.bookingRepository.numberOfBookedRoomsByCategory(cat1.categoryId(), from, until)).thenReturn(2);
        Mockito.when(this.stayRepository.getNumberOfStayRoomsByCategory(cat1.categoryId(), from, until)).thenReturn(3);

        Assertions.assertEquals(1, this.availabilityService.numberOfSuitableRooms(cat1.categoryId(), from, until));

    }

}
