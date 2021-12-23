package fhv.teamd.hotel.domain.repositories;

import fhv.teamd.hotel.domain.Booking;
import fhv.teamd.hotel.domain.ids.BookingId;
import fhv.teamd.hotel.domain.ids.CategoryId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository {

    BookingId nextIdentity();

    List<Booking> getAll();

    List<Booking> getActiveBookings();

    Optional<Booking> findById(BookingId bookingId);

    int numberOfBookedRoomsByCategory(CategoryId categoryId, LocalDateTime from, LocalDateTime until);

    void put(Booking booking);

}
