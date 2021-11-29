package fhv.teamd.hotel.application;

import fhv.teamd.hotel.application.dto.StayDTO;
import fhv.teamd.hotel.domain.Room;
import fhv.teamd.hotel.domain.Stay;
import fhv.teamd.hotel.domain.StayingState;
import fhv.teamd.hotel.domain.contactInfo.Address;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.PaymentMethod;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.ids.StayId;
import fhv.teamd.hotel.domain.repositories.StayRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
public class FrontDeskServiceTests {

    @Autowired
    private FrontDeskService frontDeskService;

    @MockBean
    private StayRepository stayRepository;

    @Test
    void given_emptyRepository_when_getAll_returnsEmpty() {
        Mockito.when(this.stayRepository.getActiveStays()).thenReturn(Collections.emptyList());
        Assertions.assertNotEquals(null, this.frontDeskService.getActiveStays());
        Assertions.assertEquals(0, this.frontDeskService.getActiveStays().size());
    }

    @Test
    void given_emptyRepository_when_getAll_returnsAll() {
        final LocalDateTime yesterday = LocalDateTime.now().minus(Period.ofDays(1));
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime tomorrow = LocalDateTime.now().plus(Period.ofDays(1));

        @SuppressWarnings("SpellCheckingInspection")
        final Address addr = new Address("musterstrasse 1", "1234", "musterort", "musterland");

        final GuestDetails guest = new GuestDetails("max", "muster", addr);

        final RepresentativeDetails rep = new RepresentativeDetails(
                "max","muster","m@mail.com", addr,"123456",
                "1111 1111 1111 1111", PaymentMethod.CreditCard);

        final Room room1 = ReflectionUtils.newInstance(Room.class);
        final Set<Room> rooms1 = new HashSet<>();
        rooms1.add(room1);

        final Room room2 = ReflectionUtils.newInstance(Room.class);
        final Set<Room> rooms2 = new HashSet<>();
        rooms2.add(room2);


        List<Stay> allStays = List.of(
            new Stay(new StayId("stay-1"), now, tomorrow, rooms1, guest, rep, StayingState.CheckedIn),
            new Stay(new StayId("stay-1"), yesterday, now, rooms2, guest, rep, StayingState.CheckedOut)
        );

        Mockito.when(this.stayRepository.getActiveStays()).thenReturn(allStays);

        final List<StayDTO> actual = this.frontDeskService.getActiveStays();

        final List<StayDTO> expected = allStays.stream().map(StayDTO::fromStay).collect(Collectors.toList());

        Assertions.assertTrue(actual.containsAll(expected) && expected.containsAll(actual));
    }

}
