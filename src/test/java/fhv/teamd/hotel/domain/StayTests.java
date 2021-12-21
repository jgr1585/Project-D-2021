package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.contactInfo.Address;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.PaymentMethod;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.exceptions.AlreadyCheckedOutException;
import fhv.teamd.hotel.domain.ids.OrganizationId;
import fhv.teamd.hotel.domain.ids.RoomId;
import fhv.teamd.hotel.domain.ids.StayId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public class StayTests {

    private Stay stay1;
    private Stay stay2;

    @BeforeEach
    public void init() {
        final LocalDateTime yesterday = LocalDateTime.now().minus(Period.ofDays(1));
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime tomorrow = LocalDateTime.now().plus(Period.ofDays(1));

        @SuppressWarnings("SpellCheckingInspection")
        final Address addr = new Address("musterstrasse 1", "1234", "musterort", "musterland");

        final GuestDetails guest = new GuestDetails("max", "muster", addr);

        final RepresentativeDetails rep = new RepresentativeDetails(
                "max","muster","m@mail.com", addr,"123456",
                "1111 1111 1111 1111", PaymentMethod.CreditCard);

        @SuppressWarnings("deprecation")
        final Room room1 = new Room(
                123L,
                new RoomId("R123"),
                DomainFactory.createCategory());
        final Set<Room> rooms1 = new HashSet<>();
        rooms1.add(room1);


        @SuppressWarnings("deprecation") final Room room2 = new Room(
                456L,
                new RoomId("R456"),
                DomainFactory.createCategory());
        final Set<Room> rooms2 = new HashSet<>();
        rooms2.add(room2);

        this.stay1 = Stay.create(new StayId("stay-1"), now, tomorrow, rooms1, guest, rep, DomainFactory.createSeason(), new OrganizationId(""));
        this.stay2 = Stay.create(new StayId("stay-2"), yesterday, now, rooms2, guest, rep, DomainFactory.createSeason(), new OrganizationId(""));

        ReflectionTestUtils.setField(this.stay2, "stayingState", StayingState.CheckedOut);

    }

    @Test
    public void testCheckOut() {
        Assertions.assertTrue(this.stay1.isActive());
        Assertions.assertFalse(this.stay2.isActive());

        try {
            this.stay1.checkOut();
        } catch (AlreadyCheckedOutException ignored) {}

        Assertions.assertThrows(AlreadyCheckedOutException.class, this.stay2::checkOut);
        Assertions.assertFalse(this.stay1.isActive());
        Assertions.assertFalse(this.stay2.isActive());
    }

    @SuppressWarnings({ "SimplifiableAssertion", "EqualsWithItself", "EqualsBetweenInconvertibleTypes", "ConstantConditions" })
    @Test
    public void testEquals() {
        Assertions.assertTrue(this.stay1.equals(this.stay1));
        Assertions.assertFalse(this.stay1.equals(this.stay2));
        Assertions.assertFalse(this.stay1.equals(""));
        Assertions.assertFalse(this.stay1.equals(null));
    }

}
