package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.contactInfo.*;
import fhv.teamd.hotel.domain.exceptions.CannotCancelException;
import fhv.teamd.hotel.domain.exceptions.CannotCheckinException;
import fhv.teamd.hotel.domain.ids.BookingId;
import fhv.teamd.hotel.domain.ids.CategoryId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.Map;

@SpringBootTest
public class BookingTests {

    private Booking booking1;
    private Booking booking2;
    private Booking booking3;

    @BeforeEach
    public void init() {
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

        final GuestDetails guest = new GuestDetails(true, GuestType.Private, "", 0,"max", "muster", addr);

        this.booking1 = new Booking(new BookingId("booking-abc"), past, past.plus(duration), categories, rep, guest);
        this.booking2 = new Booking(new BookingId("booking-def"), past, ongoing.plus(duration), categories, rep, guest);
        this.booking3 = new Booking(new BookingId("booking-ghi"), past, future.plus(duration), categories, rep, guest);

        ReflectionTestUtils.setField(this.booking2, "bookingState", BookingState.checkedIn);
        ReflectionTestUtils.setField(this.booking3, "bookingState", BookingState.cancelled);
    }


    @Test
    public void testCancelBooking() {

        try {
            this.booking1.cancelBooking();
        } catch (CannotCancelException ignored) {}

        Assertions.assertThrows(CannotCancelException.class, this.booking1::cancelBooking);

        Assertions.assertThrows(CannotCancelException.class, this.booking3::cancelBooking);

        Assertions.assertEquals(BookingState.cancelled, this.booking1.bookingState());
        Assertions.assertEquals(BookingState.checkedIn, this.booking2.bookingState());
        Assertions.assertEquals(BookingState.cancelled, this.booking3.bookingState());

    }

    @Test
    public void testCheckin() {
        try {
            this.booking1.notifyOfCheckin();
        } catch (CannotCheckinException ignored) {}

        Assertions.assertThrows(CannotCheckinException.class, this.booking2::notifyOfCheckin);

        Assertions.assertThrows(CannotCheckinException.class, this.booking3::notifyOfCheckin);

        Assertions.assertEquals(BookingState.checkedIn, this.booking1.bookingState());
        Assertions.assertEquals(BookingState.checkedIn, this.booking2.bookingState());
        Assertions.assertEquals(BookingState.cancelled, this.booking3.bookingState());
    }

    @SuppressWarnings({ "SimplifiableAssertion", "EqualsWithItself", "ConstantConditions", "EqualsBetweenInconvertibleTypes" })
    @Test
    public void testEquals() {
        Assertions.assertTrue(this.booking1.equals(this.booking1));
        Assertions.assertFalse(this.booking2.equals(this.booking3));
        Assertions.assertFalse(this.booking1.equals(null));
        Assertions.assertFalse(this.booking3.equals(""));
    }

}
