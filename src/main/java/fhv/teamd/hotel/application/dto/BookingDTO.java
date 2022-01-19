package fhv.teamd.hotel.application.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import fhv.teamd.hotel.application.dto.contactInfo.GuestDetailsDTO;
import fhv.teamd.hotel.application.dto.contactInfo.RepresentativeDetailsDTO;
import fhv.teamd.hotel.domain.Booking;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.OrganizationDetails;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class BookingDTO {

    private String id;
    private LocalDateTime fromDate;
    private LocalDateTime untilDate;

    private RepresentativeDetailsDTO representative;
    private GuestDetailsDTO guest;
    @JsonIgnore
    private String organizationId;

    private BookingDTO() {
    }

    public static BookingDTO fromBooking(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.id = booking.bookingId() == null ? null : booking.bookingId().toString();
        bookingDTO.fromDate = booking.checkInDate();
        bookingDTO.untilDate = booking.checkOutDate();
        bookingDTO.guest = GuestDetailsDTO.fromGuestDetail(booking.guestDetails());
        bookingDTO.representative = RepresentativeDetailsDTO.fromRepresentativeDetails(booking.representativeDetails());
        bookingDTO.organizationId = booking.organizationId().toString();

        return bookingDTO;
    }

    public String id() {
        return this.id;
    }

    public LocalDate fromDate() {
        return this.fromDate.toLocalDate();
    }

    public LocalDate untilDate() {
        return this.untilDate.toLocalDate();
    }

    public RepresentativeDetailsDTO representative() {
        return this.representative;
    }

    public GuestDetailsDTO guest() {
        return this.guest;
    }

    public String organizationId() {
        return this.organizationId;
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
        return Objects.equals(this.id, that.id) && Objects.equals(this.fromDate, that.fromDate) && Objects.equals(this.untilDate, that.untilDate) && Objects.equals(this.representative, that.representative) && Objects.equals(this.guest, that.guest) && Objects.equals(this.organizationId, that.organizationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.fromDate, this.untilDate, this.representative, this.guest, this.organizationId);
    }
}


