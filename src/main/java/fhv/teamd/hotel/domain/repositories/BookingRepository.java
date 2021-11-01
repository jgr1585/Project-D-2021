package fhv.teamd.hotel.domain.repositories;

import fhv.teamd.hotel.domain.Booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository {

    List<Booking> getAllBookings();
    Optional<Booking> findByBookingNumber(String bookingNumber);
    List<Booking> getBookingsByTimeFrame(LocalDateTime from, LocalDateTime until);

}
