package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.exceptions.CannotCancelException;
import fhv.teamd.hotel.domain.exceptions.CannotCheckinException;
import fhv.teamd.hotel.domain.ids.BookingId;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class Booking {

    private Long id;
    private BookingId domainId;

    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;

    private Map<Category, Integer> categories;

    private RepresentativeDetails contact;
    private GuestDetails guest;
    private BookingState bookingState;

    private Booking() {
        // hibernate
    }

    public Booking(BookingId id, LocalDateTime checkIn, LocalDateTime checkOut, Map<Category, Integer> categories,
                   RepresentativeDetails contact, GuestDetails guest) {

        this.domainId = id;
        this.checkInDate = checkIn;
        this.checkOutDate = checkOut;
        this.categories = categories;
        this.contact = contact;
        this.guest = guest;
        this.bookingState = BookingState.booked;
    }

    //Test only
    @Deprecated
    public Booking(long id, BookingId domainId, LocalDateTime checkInDate, LocalDateTime checkOutDate, Map<Category, Integer> categories, RepresentativeDetails contact, GuestDetails guest) {
        this(domainId, checkInDate, checkOutDate, categories, contact, guest);
        this.id = id;
    }

    protected Long id() {
        return this.id;
    }

    public BookingId bookingId() {
        return this.domainId;
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

    public RepresentativeDetails representativeDetails() {
        return this.contact;
    }

    public GuestDetails guestDetails() {
        return this.guest;
    }

    public BookingState bookingState() {
        return this.bookingState;
    }

    public void cancelBooking() throws CannotCancelException {
        if(this.bookingState.equals(BookingState.booked)) {
            this.bookingState = BookingState.cancelled;
        } else {
            throw new CannotCancelException();
        }
    }

    public void notifyOfCheckin() throws CannotCheckinException {
        if(this.bookingState.equals(BookingState.booked)) {
            this.bookingState = BookingState.checkedIn;
        } else {
            throw new CannotCheckinException();
        }
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
        return Objects.equals(this.id, booking.id) && Objects.equals(this.domainId, booking.domainId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.domainId);
    }
}
