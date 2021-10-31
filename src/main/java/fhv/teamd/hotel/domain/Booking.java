package fhv.teamd.hotel.domain;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.List;

public class Booking {
    private String bookingNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private List<Category> categories;

    private ContactInfo contact;
    private GuestInfo guest;

    private Booking() { }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        if(checkInDate.isAfter(this.checkOutDate)) {
            throw new InvalidParameterException();
        }

        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        if(checkOutDate.isBefore(this.checkInDate)) {
            throw new InvalidParameterException();
        }

        this.checkOutDate = checkOutDate;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public ContactInfo getContact() {
        return contact;
    }

    public void setContact(ContactInfo contact) {
        this.contact = contact;
    }

    public GuestInfo getGuest() {
        return guest;
    }

    public void setGuest(GuestInfo guest) {
        this.guest = guest;
    }
}
