package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.ids.BookingId;
import fhv.teamd.hotel.domain.services.AvailabilityService;
import fhv.teamd.hotel.domain.exceptions.HotelFullException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class Booking {
    @Autowired
    private AvailabilityService availabilityService;

    private Long id;
    private BookingId bookingId;

    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;

    private Selection selection;

    private ContactInfo contact;
    private GuestInfo guest;

    private Booking() { }

    public Booking(LocalDateTime checkIn, LocalDateTime checkOut, Selection selection,
                   ContactInfo contact, GuestInfo guest) throws HotelFullException {

        if(!this.availabilityService.checkAvailability(selection, checkIn, checkOut)) {
            throw new HotelFullException();
        }

        this.checkInDate = checkIn;
        this.checkOutDate = checkOut;
        this.selection = selection;
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

    public void editCheckInDate(LocalDateTime desiredCheckIn) throws HotelFullException {
        if(!this.availabilityService.checkAvailability(this.selection, desiredCheckIn, this.checkOutDate)) {
            throw new HotelFullException();
        }

        this.checkInDate = desiredCheckIn;
    }

    public LocalDateTime checkOutDate() {
        return this.checkOutDate;
    }

    public void editCheckOutDate(LocalDateTime desiredCheckOut) throws HotelFullException {
        if(!this.availabilityService.checkAvailability(this.selection, this.checkInDate, desiredCheckOut)) {
            throw new HotelFullException();
        }

        this.checkOutDate = desiredCheckOut;
    }

    public Selection selection() {
        return this.selection;
    }

    public void editSelection(Selection desiredSelection) throws HotelFullException {

        if(!this.availabilityService.checkAvailability(desiredSelection, this.checkInDate, this.checkOutDate)) {
            throw new HotelFullException();
        }

        this.selection = desiredSelection;
    }

    public ContactInfo contactInfo() {
        return this.contact;
    }

    public void editContactInformation(ContactInfo newContactInfo) {
        this.contact = newContactInfo;
    }

    public GuestInfo guestInfo() {
        return this.guest;
    }

    public void editGuestInformation(GuestInfo guest) {
        this.guest = guest;
    }
}
