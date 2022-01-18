package fhv.teamd.hotel.application.dto.contactInfo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;

import java.util.Objects;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class GuestDetailsDTO {
    private String firstName;
    private String lastName;
    private AddressDTO address;

    private GuestDetailsDTO() {

    }

    public static GuestDetailsDTO fromGuestDetail(GuestDetails details) {
        GuestDetailsDTO guestDetailsDTO = new GuestDetailsDTO();

        guestDetailsDTO.firstName = details.firstName();
        guestDetailsDTO.lastName = details.lastName();
        guestDetailsDTO.address = AddressDTO.fromAddress(details.address());

        return guestDetailsDTO;
    }

    public String firstName() {
        return this.firstName;
    }

    public String lastName() {
        return this.lastName;
    }

    public AddressDTO address() {
        return this.address;
    }

    @Override
    public String toString() {
        return this.firstName + ' ' + this.lastName + "\r\n" + this.address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final GuestDetailsDTO that = (GuestDetailsDTO) o;
        return Objects.equals(this.firstName, that.firstName) && Objects.equals(this.lastName, that.lastName) && Objects.equals(this.address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.firstName, this.lastName, this.address);
    }
}
