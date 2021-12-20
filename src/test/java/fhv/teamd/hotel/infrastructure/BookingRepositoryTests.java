package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.*;
import fhv.teamd.hotel.domain.ids.BookingId;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.ids.OrganizationId;
import fhv.teamd.hotel.domain.repositories.BookingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
@Transactional
public class BookingRepositoryTests {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EntityManager entityManager;

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
        List<Booking> expected = BaseRepositoryData.bookings();
        List<Booking> actual = this.bookingRepository.getAll();

        Assertions.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    void given_booking_when_getActiveBookings_return_allActiveBookings() {
        List<Booking> expected = BaseRepositoryData.bookings();
        List<Booking> actual = this.bookingRepository.getActiveBookings();

        Assertions.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    void given_booking_when_findById_return_bookingById() {
        Optional<Booking> expected = BaseRepositoryData.bookings()
                .stream()
                .filter(b -> b.bookingId().toString().equals("dom-id-book-111"))
                .findFirst();

        Optional<Booking> actual = this.bookingRepository.findById(new BookingId("dom-id-book-111"));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void given_none_when_findById_return_EmptyOptional() {
        Assertions.assertEquals(Optional.empty(), this.bookingRepository.findById(DomainFactory.createBookingId()));
    }

    @Test
    void given_booking_when_numberOfBookedRoomsByCategory_return_getNumberOfAvailableRooms() {
        List<Booking> baseRepoBookings = BaseRepositoryData.bookings();

        CategoryId categoryId = new CategoryId("dom-id-cat-111");
        LocalDateTime from = LocalDateTime.parse("2021-12-26T10:00:00");
        LocalDateTime until = LocalDateTime.parse("2021-12-30T10:00:00");

        int expectedUsedRooms = baseRepoBookings
                .stream()
                .filter(booking -> booking.checkInDate().isBefore(until) && booking.checkOutDate().isAfter(from))
                .flatMap(booking -> booking.selection().entrySet().stream())
                .filter(entry -> entry.getKey().categoryId().equals(categoryId))
                .map(Map.Entry::getValue)
                .reduce(Integer::sum)
                .orElse(0);

        int actualUsedRooms = this.bookingRepository.numberOfBookedRoomsByCategory(categoryId, from, until);

        Assertions.assertEquals(expectedUsedRooms, actualUsedRooms);
    }

    @Test
    void given_booking_when_putNewBooking_return_allBookings() {
        List<Category> categories = BaseRepositoryData.categories();
        List<Booking> baseRepoBookings = BaseRepositoryData.bookings();

        Booking firstBooking = baseRepoBookings.get(0);
        Booking newBooking = new Booking(
                this.bookingRepository.nextIdentity(),
                firstBooking.checkInDate(),
                firstBooking.checkOutDate(),
                Map.of(categories.get(0), 1),
                firstBooking.representativeDetails(),
                firstBooking.guestDetails(),
                new OrganizationId("")
        );

        List<Booking> expected = new ArrayList<>(List.of(newBooking));
        expected.addAll(BaseRepositoryData.bookings());

        this.bookingRepository.put(newBooking);
        this.entityManager.flush();

        List<Booking> actual = this.bookingRepository.getAll();

        Assertions.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }
}
