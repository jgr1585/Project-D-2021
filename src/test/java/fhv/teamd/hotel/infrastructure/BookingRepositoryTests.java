package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.Booking;
import fhv.teamd.hotel.domain.BookingState;
import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.contactInfo.Address;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.PaymentMethod;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.ids.BookingId;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.repositories.BookingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class BookingRepositoryTests {

    @Autowired
    private BookingRepository bookingRepository;

    @MockBean
    private EntityManager entityManager;

    private Booking booking1;
    private Booking booking2;
    private Booking booking3;

    private List<Booking> bookings;

    @BeforeEach
    void init() {
        final LocalDateTime past = LocalDateTime.now().minus(Period.ofYears(1));
        final LocalDateTime ongoing = LocalDateTime.now().minus(Period.ofDays(1));
        final LocalDateTime future = LocalDateTime.now().plus(Period.ofYears(1));
        final Period duration = Period.ofWeeks(1);

        //noinspection deprecation
        final Map<Category, Integer> categories = Map.of(
                new Category(1L, new CategoryId("abc"), "category-abc", "halo", 99),
                3
        );

        @SuppressWarnings("SpellCheckingInspection")
        final Address addr = new Address("musterstrasse 1", "1234", "musterort", "musterland");

        final RepresentativeDetails rep = new RepresentativeDetails(
                "max","muster","m@mail.com", addr,"123456",
                "1111 1111 1111 1111", PaymentMethod.CreditCard);

        final GuestDetails guest = new GuestDetails("max", "muster", addr);

        this.booking1 = new Booking(new BookingId("booking-abc"), past, past.plus(duration), categories, rep, guest);
        this.booking2 = new Booking(new BookingId("booking-def"), past, ongoing.plus(duration), categories, rep, guest);
        this.booking3 = new Booking(new BookingId("booking-ghi"), past, future.plus(duration), categories, rep, guest);

        ReflectionTestUtils.setField(this.booking2, "bookingState", BookingState.checkedIn);
        ReflectionTestUtils.setField(this.booking3, "bookingState", BookingState.cancelled);

        this.bookings = new LinkedList<>();
        this.bookings.add(this.booking1);
        this.bookings.add(this.booking2);
        this.bookings.add(this.booking3);

    }

    @Test
    void text_nextIdentity() {
        BookingId bookingId1 = this.bookingRepository.nextIdentity();
        BookingId bookingId2 = this.bookingRepository.nextIdentity();


        Assertions.assertNotNull(bookingId1);
        Assertions.assertNotNull(bookingId2);
        Assertions.assertNotNull(bookingId1.toString());
        Assertions.assertNotNull(bookingId2.toString());
        Assertions.assertNotEquals(bookingId1, bookingId2);
    }

    @Test
    void given_booking_when_getAll_return_all() {
        //noinspection JpaQlInspection
        Mockito.when(this.entityManager
                .createQuery("SELECT b FROM Booking b", Booking.class)
                        .getResultList())
                .thenReturn(this.bookings);

        List<Booking> actual = this.bookingRepository.getAll();

        Assertions.assertTrue(actual.containsAll(this.bookings) && this.bookings.containsAll(actual));
    }
}
