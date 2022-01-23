package fhv.teamd.hotel.application.dto.contactInfo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import fhv.teamd.hotel.domain.contactInfo.Address;

import java.util.Objects;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AddressDTO {

    private String street;
    private String zip;
    private String city;
    private String country;

    private AddressDTO() {

    }

    public static AddressDTO fromAddress(Address address) {
        AddressDTO addressDTO = new AddressDTO();

        addressDTO.street = address.street();
        addressDTO.zip = address.zip();
        addressDTO.city = address.city();
        addressDTO.country = address.country();

        return addressDTO;
    }

    public String street() {
        return this.street;
    }

    public String zip() {
        return this.zip;
    }

    public String city() {
        return this.city;
    }

    public String country() {
        return this.country;
    }

    @Override
    public String toString() {
        return String.join("\r\n", this.street, this.zip, this.city, this.country);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final AddressDTO address = (AddressDTO) o;
        return Objects.equals(this.street, address.street) && Objects.equals(this.zip, address.zip) && Objects.equals(this.city, address.city) && Objects.equals(this.country, address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.street, this.zip, this.city, this.country);
    }
}
