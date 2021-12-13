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
}
