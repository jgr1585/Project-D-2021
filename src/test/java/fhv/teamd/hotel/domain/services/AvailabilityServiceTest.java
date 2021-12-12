package fhv.teamd.hotel.domain.services;

import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.DomainFactory;
import fhv.teamd.hotel.domain.Room;
import fhv.teamd.hotel.domain.Stay;
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
import java.util.Set;

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
        final Category cat1 = DomainFactory.createCategory();
        final LocalDateTime from = LocalDateTime.now();
        final LocalDateTime until = LocalDateTime.now().plus(Period.ofDays(1));
        final List<Room> rooms = new LinkedList<>();
        rooms.add(DomainFactory.createRoomInCategory(cat1));
        rooms.add(DomainFactory.createRoomInCategory(cat1));
        rooms.add(DomainFactory.createRoomInCategory(cat1));
        rooms.add(DomainFactory.createRoomInCategory(cat1));
        rooms.add(DomainFactory.createRoomInCategory(cat1));
        rooms.add(DomainFactory.createRoomInCategory(cat1));


        Mockito.when(this.roomRepository.getByCategory(cat1.categoryId())).thenReturn(rooms);
        Mockito.when(this.bookingRepository.numberOfBookedRoomsByCategory(cat1.categoryId(), from, until)).thenReturn(2);
        Mockito.when(this.stayRepository.numberOfStayRoomsByCategory(cat1.categoryId(), from, until)).thenReturn(3);

        Assertions.assertEquals(1, this.availabilityService.numberOfSuitableRooms(cat1.categoryId(), from, until));

    }

    @Test
    void given_room_when_isAvailable_then_retrun_bool() {
        final Category cat1 = DomainFactory.createCategory();
        final LocalDateTime from = LocalDateTime.now();
        final LocalDateTime until = LocalDateTime.now().plus(Period.ofDays(1));
        final List<Room> rooms = new LinkedList<>();

        for (int i = 0; i < 12; i++) {
            rooms.add(DomainFactory.createRoomInCategory(cat1));
        }

        Mockito.when(this.roomRepository.getByCategory(cat1.categoryId())).thenReturn(rooms);
        Mockito.when(this.bookingRepository.numberOfBookedRoomsByCategory(cat1.categoryId(), from, until)).thenReturn(2);
        Mockito.when(this.stayRepository.numberOfStayRoomsByCategory(cat1.categoryId(), from, until)).thenReturn(3);

        Assertions.assertTrue(this.availabilityService.isAvailable(cat1.categoryId(), from, until, 1));
        Assertions.assertTrue(this.availabilityService.isAvailable(cat1.categoryId(), from, until, 2));
    }

    @Test
    void give_category_when_find_suitable_Rooms_then_get_Rooms() {
        final Category cat1 = DomainFactory.createCategory();
        final LocalDateTime from = LocalDateTime.now();
        final LocalDateTime until = LocalDateTime.now().plus(Period.ofDays(1));
        final List<Room> rooms = new LinkedList<>();

        for (int i = 0; i < 12; i++) {
            rooms.add(DomainFactory.createRoomInCategory(cat1));
        }

        final Stay stay1 = DomainFactory.createStayInRooms(Set.of(rooms.get(0), rooms.get(2), rooms.get(3)), from, until);
        final Stay stay2 = DomainFactory.createStayInRooms(Set.of(rooms.get(4), rooms.get(7), rooms.get(8), rooms.get(9)), from, until);

        final List<Stay> stays = List.of(stay1, stay2);

        final List<Room> expected = new LinkedList<>(rooms);
        final int[] removedRooms = {9, 8, 7, 4, 3, 2, 0};

        for (int i: removedRooms) {
            expected.remove(i);
        }

        Mockito.when(this.roomRepository.getByCategory(cat1.categoryId())).thenReturn(rooms);
        Mockito.when(this.stayRepository.activeStaysWithOverlappingDuration(from, until)).thenReturn(stays);

        final List<Room> actual1 = this.availabilityService.suitableRooms(cat1.categoryId(), from, until, 3);
        final List<Room> actual2 = this.availabilityService.suitableRooms(cat1.categoryId(), from, until, 5);
        final List<Room> actual3 = this.availabilityService.suitableRooms(cat1.categoryId(), from, until, 8);

        Assertions.assertTrue(actual1.size() == 3 && expected.containsAll(actual1));
        Assertions.assertTrue(actual2.size() == 5 && expected.containsAll(actual2));
        Assertions.assertTrue(actual3.size() == 5 && expected.containsAll(actual3));

    }

    @Test
    void give_cateory_when_check_Availability_then_return_bool() {
        final Category cat1 = DomainFactory.createCategory();
        final LocalDateTime from = LocalDateTime.now();
        final LocalDateTime until = LocalDateTime.now().plus(Period.ofDays(1));
        final List<Room> rooms = new LinkedList<>();

        for (int i = 0; i < 12; i++) {
            rooms.add(DomainFactory.createRoomInCategory(cat1));
        }

        final Stay stay1 = DomainFactory.createStayInRooms(Set.of(rooms.get(0), rooms.get(2), rooms.get(3)), from, until);
        final Stay stay2 = DomainFactory.createStayInRooms(Set.of(rooms.get(4), rooms.get(7), rooms.get(8), rooms.get(9)), from, until);

        final List<Stay> stays = List.of(stay1, stay2);

        Mockito.when(this.stayRepository.activeStaysWithOverlappingDuration(from, until)).thenReturn(stays);

        final Set<Room> set1 = Set.of(rooms.get(1), rooms.get(5)); //Expected: True
        final Set<Room> set2 = Set.of(rooms.get(1), rooms.get(5), rooms.get(6), rooms.get(10), rooms.get(11)); //Expected: True
        final Set<Room> set3 = Set.of(rooms.get(1), rooms.get(3)); //Expected: False

        Assertions.assertTrue(this.availabilityService.areAvailable(set1, from, until));
        Assertions.assertTrue(this.availabilityService.areAvailable(set2, from, until));
        Assertions.assertFalse(this.availabilityService.areAvailable(set3, from, until));
    }



}
