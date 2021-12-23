package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.DomainFactory;
import fhv.teamd.hotel.domain.Room;
import fhv.teamd.hotel.domain.Stay;
import fhv.teamd.hotel.domain.ids.BillId;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.ids.OrganizationId;
import fhv.teamd.hotel.domain.ids.StayId;
import fhv.teamd.hotel.domain.repositories.StayRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
public class StayRepositoryTests {

    @Autowired
    private StayRepository stayRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void text_nextIdentity() {
        StayId stayId1 = this.stayRepository.nextIdentity();
        StayId stayId2 = this.stayRepository.nextIdentity();


        Assertions.assertNotNull(stayId1);
        Assertions.assertNotNull(stayId2);
        Assertions.assertNotNull(stayId1.toString());
        Assertions.assertNotNull(stayId2.toString());
        Assertions.assertNotEquals(stayId1, stayId2);
    }

    @Test
    void given_stay_when_getAll_return_all() {
        List<Stay> expected = BaseRepositoryData.stays();
        List<Stay> actual = this.stayRepository.getAll();

        Assertions.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    void given_stay_when_getActiveStays_return_allActiveStays() {
        List<Stay> expected = BaseRepositoryData.stays();
        List<Stay> actual = this.stayRepository.getActiveStays();

        Assertions.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    void given_stay_when_activeStays_return_allStaysWithOverlappingDuration() {
        List<Stay> baseRepoStays = BaseRepositoryData.stays();

        LocalDateTime from = LocalDateTime.parse("2021-11-13T10:00:00");
        LocalDateTime until = LocalDateTime.parse("2021-11-20T10:00:00");

        List<Stay> expected = baseRepoStays
                .stream()
                .filter(stay -> stay.checkIn().isBefore(until) && stay.expectedCheckOut().isAfter(from))
                .collect(Collectors.toList());

        List<Stay> actual = this.stayRepository.activeStaysWithOverlappingDuration(from, until);

        Assertions.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    void given_stay_when_findById_return_stayById() {
        Optional<Stay> expected = BaseRepositoryData.stays()
                .stream()
                .filter(b -> b.stayId().toString().equals("dom-id-stay-111"))
                .findFirst();

        Optional<Stay> actual = this.stayRepository.findById(new StayId("dom-id-stay-111"));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void given_none_when_findById_return_EmptyOptional() {
        Assertions.assertEquals(Optional.empty(), this.stayRepository.findById(DomainFactory.createStayId()));
    }

    @Test
    void given_stay_when_numberOfStayRoomsByCategory_return_getNumberOfAvailableRooms() {
        List<Stay> baseRepoStays = BaseRepositoryData.stays();

        CategoryId categoryId = new CategoryId("dom-id-cat-111");
        LocalDateTime from = LocalDateTime.parse("2021-11-13T10:00:00");
        LocalDateTime until = LocalDateTime.parse("2021-11-20T10:00:00");

        int expectedUsedRooms = (int) baseRepoStays
                .stream()
                .filter(stay -> stay.checkIn().isBefore(until) && stay.expectedCheckOut().isAfter(from))
                .flatMap(stay -> stay.rooms().stream())
                .filter(room -> room.category().categoryId().equals(categoryId))
                .count();

        int actualUsedRooms = this.stayRepository.numberOfStayRoomsByCategory(categoryId, from, until);

        Assertions.assertEquals(expectedUsedRooms, actualUsedRooms);
    }

    @Test
    void given_stay_when_putNewStay_return_allStays() {
        List<Room> rooms = BaseRepositoryData.rooms();
        List<Stay> baseRepoStays = BaseRepositoryData.stays();

        Stay firstStay = baseRepoStays.get(0);
        Stay newStay = Stay.create(
                this.stayRepository.nextIdentity(),
                firstStay.checkIn(),
                firstStay.expectedCheckOut(),
                Set.of(rooms.get(0)),
                firstStay.guestDetails(),
                firstStay.representativeDetails(),
                DomainFactory.getSeasonOf(firstStay.checkIn()),
                new OrganizationId(""),
                new BillId("6421321")
        );

        List<Stay> expected = new ArrayList<>(List.of(newStay));
        expected.addAll(BaseRepositoryData.stays());

        this.stayRepository.put(newStay);
        this.entityManager.flush();

        List<Stay> actual = this.stayRepository.getAll();

        Assertions.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }
}
