package fhv.teamd.hotel.domain;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;

public class Booking {
    private String bookingNumber;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;

    private List<Category> categories;

    private ContactInfo contact;
    private GuestInfo guest;

    private Booking() { }

    public String getBookingNumber() {
        return this.bookingNumber;
    }

    public LocalDateTime getCheckInDate() {
        return this.checkInDate;
    }

    public void setCheckInDate(LocalDateTime checkInDate) {
        if(checkInDate.isAfter(this.checkOutDate)) {
            throw new InvalidParameterException();
        }

        this.checkInDate = checkInDate;
    }

    public LocalDateTime getCheckOutDate() {
        return this.checkOutDate;
    }

    public void setCheckOutDate(LocalDateTime checkOutDate) {
        if(checkOutDate.isBefore(this.checkInDate)) {
            throw new InvalidParameterException();
        }

        this.checkOutDate = checkOutDate;
    }

    public List<Category> getCategories() {
        return this.categories;
    }

    public ContactInfo getContact() {
        return this.contact;
    }

    public void setContact(ContactInfo contact) {
        this.contact = contact;
    }

    public GuestInfo getGuest() {
        return this.guest;
    }

    public void setGuest(GuestInfo guest) {
        this.guest = guest;
    }
}
