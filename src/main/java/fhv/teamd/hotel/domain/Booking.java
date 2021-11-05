package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.ids.BookingId;
import fhv.teamd.hotel.domain.services.AvailabilityService;
import fhv.teamd.hotel.domain.exceptions.HotelFullException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

public class Booking {

    private Long id;
    private BookingId bookingId;

    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;

    private Map<Category, Integer> categories;

    private ContactInfo contact;
    private GuestInfo guest;

    private Booking() { }

    public Booking(LocalDateTime checkIn, LocalDateTime checkOut, Map<Category, Integer> categories,
                   ContactInfo contact, GuestInfo guest) {


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
}
