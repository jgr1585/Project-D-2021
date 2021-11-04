package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.*;
import fhv.teamd.hotel.domain.exceptions.HotelFullException;
import fhv.teamd.hotel.domain.ids.BookingId;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

public class BookingRepositoryImpl {

    private final List<Booking> placeholder;

    private BookingRepositoryImpl() throws HotelFullException {
        this.placeholder = List.of(
                new Booking(LocalDateTime.of(2021, 11, 12, 13, 0),
                        LocalDateTime.of(2021, 11, 15, 10, 0),
                        new Selection(Map.of(
                                new Category("Test-Category1", "kkkkkkk", 60), 2
                        )),
                        new ContactInfo(
                                "Max Mustermann",
                                "muster@mail.com",
                                "musterstraße 1",
                                "12345102"),
                        new GuestInfo(
                                "Mustergast",
                                "musteradresse 3232"))
        );
    }

    public BookingId nextIdentity() {
        return new BookingId(java.util.UUID.randomUUID().toString());
    }

    public List<Booking> getAllBookings() {
        return Collections.unmodifiableList(this.placeholder);
    }

    public Optional<Booking> findByBookingId(BookingId id) {
        for (Booking b : this.placeholder) {
            if (b.bookingId().equals(id)) {
                return Optional.of(b);
            }
        }
        return Optional.empty();
    }

    public List<Booking> getBookingsByCheckInDate(LocalDateTime from, LocalDateTime until) {
        List<Booking> list = new ArrayList<>();
        for (Booking b : this.placeholder) {
            if (from.isBefore(b.checkInDate()) && until.isAfter(b.checkInDate())) {
                list.add(b);
            }
        }
        return Collections.unmodifiableList(list);
    }
}
