package fhv.teamd.hotel.domain.repositories;

import fhv.teamd.hotel.domain.Booking;
import fhv.teamd.hotel.domain.ids.BookingId;
import fhv.teamd.hotel.domain.ids.CategoryId;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository {

    BookingId nextIdentity();

    List<Booking> getAllBookings();

    Optional<Booking> findByBookingId(BookingId bookingId);

    List<Booking> getBookingsByCheckInDate(LocalDateTime from, LocalDateTime until);

    void put(Booking booking);

    void remove(BookingId bookingId) throws EntityNotFoundException;

}
