package fhv.teamd.hotel.application;

import fhv.teamd.hotel.application.dto.RoomDTO;
import fhv.teamd.hotel.domain.DomainFactory;
import fhv.teamd.hotel.domain.Room;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.services.AvailabilityService;
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
import java.util.stream.Collectors;

@SpringBootTest
public class RoomSuggestionServiceTests {

    @Autowired
    private RoomSuggestionService roomSuggestionService;

    @MockBean
    private AvailabilityService availabilityService;

    @Test
    void given_Catygory_when_findSuitableRooms_find_rooms() {
        final CategoryId cat = DomainFactory.CreateCategoryId();
        final List<Room> rooms = new LinkedList<>();
        rooms.add(DomainFactory.CreateRoom());
        rooms.add(DomainFactory.CreateRoom());
        rooms.add(DomainFactory.CreateRoom());
        final LocalDateTime later = LocalDateTime.now().plus(Period.ofDays(1));

        final List<RoomDTO> expected = rooms.stream().map(RoomDTO::fromRoom).collect(Collectors.toList());

        Mockito.when(this.availabilityService
                .suitableRooms(cat, LocalDateTime.now(), later, 3)
                .stream()
                .map(RoomDTO::fromRoom)
                .collect(Collectors.toList())).thenReturn(expected);

        Assertions.assertEquals(expected, this.roomSuggestionService.findSuitableRooms(cat.toString(), LocalDateTime.now(), later, 3));

    }

}
