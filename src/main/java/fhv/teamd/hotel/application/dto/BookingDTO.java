package fhv.teamd.hotel.application.dto;

import fhv.teamd.hotel.domain.Booking;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class BookingDTO {

    private String id;
    private LocalDateTime fromDate;
    private LocalDateTime untilDate;
    private RepresentativeDetails representative;
    private GuestDetails guest;

    public String id() {
        return this.id;
    }

    public LocalDate fromDate() {
        return this.fromDate.toLocalDate();
    }

    public LocalDate untilDate() {
        return this.untilDate.toLocalDate();
    }

    public RepresentativeDetails representative() {
        return this.representative;
    }

    public GuestDetails guest() {
        return this.guest;
    }

    private BookingDTO() {
    }

    public static BookingDTO fromBooking(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.id = booking.bookingId() == null ? null : booking.bookingId().toString();
        bookingDTO.fromDate = booking.checkInDate();
        bookingDTO.untilDate = booking.checkOutDate();
        bookingDTO.guest = booking.guestDetails();
        bookingDTO.representative = booking.representativeDetails();

        return bookingDTO;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final BookingDTO that = (BookingDTO) o;
        return Objects.equals(this.id, that.id) && Objects.equals(this.fromDate, that.fromDate) && Objects.equals(this.untilDate, that.untilDate) && Objects.equals(this.representative, that.representative) && Objects.equals(this.guest, that.guest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.fromDate, this.untilDate, this.representative, this.guest);
    }
}


