package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.ids.BookingId;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class Booking {

    private Long id;
    private BookingId bookingId;

    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;

    private Map<Category, Integer> categories;

    private ContactInfo contact;
    private GuestInfo guest;

    private Booking() {
        // hibernate
    }

    public Booking(BookingId id, LocalDateTime checkIn, LocalDateTime checkOut, Map<Category, Integer> categories,
                   ContactInfo contact, GuestInfo guest) {

        this.bookingId = id;
        this.checkInDate = checkIn;
        this.checkOutDate = checkOut;
        this.categories = categories;
        this.contact = contact;
        this.guest = guest;
    }

    protected Long id() {
        return this.id;
    }

    public BookingId bookingId() {
        return this.bookingId;
    }

    public LocalDateTime checkInDate() {
        return this.checkInDate;
    }

    public LocalDateTime checkOutDate() {
        return this.checkOutDate;
    }

    public Map<Category, Integer> selection() {
        return Collections.unmodifiableMap(this.categories);
    }

    public ContactInfo contactInfo() {
        return this.contact;
    }

    public GuestInfo guestInfo() {
        return this.guest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Booking booking = (Booking) o;
        return Objects.equals(this.id, booking.id) && Objects.equals(this.bookingId, booking.bookingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.bookingId);
    }
}
