package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.Booking;
import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.DomainFactory;
import fhv.teamd.hotel.domain.Room;
import fhv.teamd.hotel.domain.ids.BookingId;
import fhv.teamd.hotel.domain.repositories.RoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.*;

@SpringBootTest
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class RoomRepositoryTests {

    @Autowired
    private RoomRepository roomRepository;

    @Test
    void given_room_when_getAll_return_all() {
        List<Room> expected = BaseRepositoryData.rooms();
        List<Room> actual = this.roomRepository.getAll();

        Assertions.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    void given_room_when_findById_return_roomById() {
        List<Room> rooms = BaseRepositoryData.rooms();

        rooms.forEach(room -> {
            @SuppressWarnings("OptionalGetWithoutIsPresent")
            Room actual = this.roomRepository.findById(room.roomId()).get();

            Assertions.assertEquals(room, actual);
        });
    }

    @Test
    void given_none_when_findById_return_EmptyOptional() {
        Assertions.assertEquals(Optional.empty(), this.roomRepository.findById(DomainFactory.createRoomId()));
    }

    @Test
    void given_Category_when_get_room_by_category_return_Rooms() {
        Map<Category, List<Room>> roomsByCategory = BaseRepositoryData.rooms()
                .stream().collect(
                        groupingBy(Room::category, toList())
        );

        roomsByCategory.forEach(((category, expected) -> {
            List<Room> actual = this.roomRepository.getByCategory(category.categoryId());

            Assertions.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
        }));
    }

    @Test
    void given_Category_when_get_room_max_rooms_by_category_return_Rooms() {
        int[] maxPerRound = {1, 2, 5, 7, 10, 100, 200};
        Map<Category, List<Room>> roomsByCategory = BaseRepositoryData.rooms()
                .stream().collect(
                        groupingBy(Room::category, toList())
                );

        roomsByCategory.forEach(((category, expected) -> {
            for (int i: maxPerRound) {
                int expectedSize = Integer.min(expected.size(), i);

                List<Room> actual = this.roomRepository.getByCategory(category.categoryId(), expectedSize);

                Assertions.assertTrue(expected.containsAll(actual) && actual.size() == expectedSize);
            }
        }));
    }

}
