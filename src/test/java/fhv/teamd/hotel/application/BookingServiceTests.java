package fhv.teamd.hotel.application;

import fhv.teamd.hotel.application.dto.BookingDTO;
import fhv.teamd.hotel.domain.Booking;
import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.contactInfo.Address;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.PaymentMethod;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.ids.BookingId;
import fhv.teamd.hotel.domain.repositories.BookingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
public class BookingServiceTests {

    @Autowired
    private BookingService bookingService;

    @MockBean
    private BookingRepository bookingRepository;

    @Test
    void given_emptyRepository_when_getAll_returnsEmpty() {

        Mockito.when(this.bookingRepository.getAllBookings()).thenReturn(Collections.emptyList());
        Assertions.assertNotEquals(null, this.bookingService.getActiveBookings());
        Assertions.assertEquals(0, this.bookingService.getActiveBookings().size());

    }

    @Test
    void given_repository_when_getAll_returnsAll() {

        final LocalDateTime past = LocalDateTime.now().minus(Period.ofYears(1));
        final LocalDateTime ongoing = LocalDateTime.now().minus(Period.ofDays(1));
        final LocalDateTime future = LocalDateTime.now().plus(Period.ofYears(1));
        final Period duration = Period.ofWeeks(1);

        final Map<Category, Integer> categories = Map.of(
                new Category("category-abc", "halo", 99),
                3
        );

        @SuppressWarnings("SpellCheckingInspection")
        final Address addr = new Address("musterstrasse 1", "1234", "musterort", "musterland");


        final RepresentativeDetails rep = new RepresentativeDetails(
                "max","muster","m@mail.com", addr,"123456",
                "1111 1111 1111 1111", PaymentMethod.CreditCard);

        final GuestDetails guest = new GuestDetails("max", "muster", addr);

        final List<Booking> allBookings = List.of(
                new Booking(new BookingId("booking-abc"), past, past.plus(duration), categories, rep, guest),
                new Booking(new BookingId("booking-def"), past, ongoing.plus(duration), categories, rep, guest),
                new Booking(new BookingId("booking-ghi"), past, future.plus(duration), categories, rep, guest)
        );

        Mockito.when(this.bookingRepository.getAllBookings()).thenReturn(allBookings);

        final List<BookingDTO> actual = this.bookingService.getActiveBookings();

        final List<BookingDTO> expected
                = allBookings.stream().map(BookingDTO::fromBooking).collect(Collectors.toList());

        Assertions.assertTrue(actual.containsAll(expected) && expected.containsAll(actual));

    }

}
