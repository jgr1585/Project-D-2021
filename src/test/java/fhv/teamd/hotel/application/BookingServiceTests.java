package fhv.teamd.hotel.application;

import fhv.teamd.hotel.application.dto.BookingDTO;
import fhv.teamd.hotel.application.dto.DetailedBookingDTO;
import fhv.teamd.hotel.application.exceptions.CategoryNotAvailableException;
import fhv.teamd.hotel.application.exceptions.InvalidIdException;
import fhv.teamd.hotel.domain.Booking;
import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.DomainFactory;
import fhv.teamd.hotel.domain.contactInfo.*;
import fhv.teamd.hotel.domain.ids.BookingId;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.repositories.BookingRepository;
import fhv.teamd.hotel.domain.repositories.CategoryRepository;
import fhv.teamd.hotel.domain.services.AvailabilityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
public class BookingServiceTests {

    @Autowired
    private BookingService bookingService;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private AvailabilityService availabilityService;

    @Captor
    private ArgumentCaptor<Booking> actualBooking;

    private LocalDateTime past;
    private LocalDateTime ongoing;
    private LocalDateTime future;
    private Period duration;
    private RepresentativeDetails rep;
    private GuestDetails guest;

    @BeforeEach
    void init() {
        this.past = LocalDateTime.now().minus(Period.ofYears(1));
        this.ongoing = LocalDateTime.now().minus(Period.ofDays(1));
        this.future = LocalDateTime.now().plus(Period.ofYears(1));
        this.duration = Period.ofWeeks(1);
    }

    @Test
    void given_emptyRepository_when_getAll_returnsEmpty() {

        Mockito.when(this.bookingRepository.getAll()).thenReturn(Collections.emptyList());
        Assertions.assertNotEquals(null, this.bookingService.getActiveBookings());
        Assertions.assertEquals(0, this.bookingService.getActiveBookings().size());

        //noinspection SpellCheckingInspection
        final Address addr = new Address("musterstrasse 1", "1234", "musterort", "musterland");


        this.rep = new RepresentativeDetails(
                "max","muster","m@mail.com", addr,"123456",
                "1111 1111 1111 1111", PaymentMethod.CreditCard);

        this.guest = new GuestDetails(true, GuestType.Private, "", 0,"max", "muster", addr);

    }

    @Test
    void given_repository_when_getAll_returnsAll() {
        
        final Map<Category, Integer> categories = Map.of(
                DomainFactory.createCategory(),
                3
        );


        final List<Booking> allBookings = List.of(
                new Booking(new BookingId("booking-abc"), this.past, this.past.plus(this.duration), categories, this.rep, this.guest),
                new Booking(new BookingId("booking-def"), this.past, this.ongoing.plus(this.duration), categories, this.rep, this.guest),
                new Booking(new BookingId("booking-ghi"), this.past, this.future.plus(this.duration), categories, this.rep, this.guest)
        );

        Mockito.when(this.bookingRepository.getActiveBookings()).thenReturn(allBookings);

        final List<BookingDTO> actual = this.bookingService.getActiveBookings();

        final List<BookingDTO> expected
                = allBookings.stream().map(BookingDTO::fromBooking).collect(Collectors.toList());

        Assertions.assertTrue(actual.containsAll(expected) && expected.containsAll(actual));

    }

    @Test
    void given_newBooking_when_book_then_CreateBooking() {
        final Map<String, Integer> categoryIdsAndAmounts = new HashMap<>();
        final Category cat1 = DomainFactory.createCategory();
        final Category cat2 = DomainFactory.createCategory();
        final Map<Category, Integer> categoriesAndAmounts = new HashMap<>();
        final BookingId bookingId = DomainFactory.createBookingId();

        categoriesAndAmounts.put(cat1, 1);

        categoryIdsAndAmounts.put(cat1.categoryId().toString(), 1);
        categoryIdsAndAmounts.put(cat2.categoryId().toString(), 0);

        final Booking expected = new Booking(bookingId, this.ongoing, this.future, categoriesAndAmounts, this.rep, this.guest);



        Mockito.when(this.categoryRepository.findById(cat1.categoryId())).thenReturn(Optional.of(cat1));
        Mockito.when(this.categoryRepository.findById(cat2.categoryId())).thenReturn(Optional.of(cat2));

        Mockito.when(this.availabilityService.isAvailable(cat1.categoryId(), this.ongoing, this.future, 1)).thenReturn(true);
        Mockito.when(this.availabilityService.isAvailable(cat2.categoryId(), this.ongoing, this.future, 0)).thenReturn(true);

        Mockito.when(this.bookingRepository.nextIdentity()).thenReturn(bookingId);


        Assertions.assertDoesNotThrow(() -> this.bookingService.book(categoryIdsAndAmounts, this.ongoing, this.future, this.guest, this.rep));

        Assertions.assertThrows(CategoryNotAvailableException.class, () -> {
            categoryIdsAndAmounts.put(cat2.categoryId().toString(), 2);
            this.bookingService.book(categoryIdsAndAmounts, this.ongoing, this.future, this.guest, this.rep);
        });

        Assertions.assertThrows(InvalidIdException.class, () -> {
            categoryIdsAndAmounts.put(cat2.categoryId().toString(), 0);
            categoryIdsAndAmounts.put(DomainFactory.createCategoryId().toString(), 2);
            this.bookingService.book(categoryIdsAndAmounts, this.ongoing, this.future, this.guest, this.rep);
        });

        Mockito.verify(this.bookingRepository).put(this.actualBooking.capture());

        Assertions.assertEquals(expected, this.actualBooking.getValue());

    }

    @Test
    void give_booking_when_getDetails_then_getDetails() {
        final Booking booking1 = DomainFactory.createBooking();
        final Booking booking2 = DomainFactory.createBooking();
        final BookingId nonExistingBooking = DomainFactory.createBookingId();

        Mockito.when(this.bookingRepository.findById(booking1.bookingId())).thenReturn(Optional.of(booking1));
        Mockito.when(this.bookingRepository.findById(booking2.bookingId())).thenReturn(Optional.of(booking2));

        Assertions.assertEquals(Optional.of(booking1).map(DetailedBookingDTO::fromBooking), this.bookingService.getDetails(booking1.bookingId().toString()));
        Assertions.assertEquals(Optional.of(booking2).map(DetailedBookingDTO::fromBooking), this.bookingService.getDetails(booking2.bookingId().toString()));

        Assertions.assertEquals(Optional.empty(), this.bookingService.getDetails(nonExistingBooking.toString()));
    }

}
