package fhv.teamd.hotel.application.dto.contactInfo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import fhv.teamd.hotel.domain.contactInfo.PaymentMethod;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;

import java.util.Objects;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RepresentativeDetailsDTO {

    private String firstName;
    private String lastName;
    private String email;
    private AddressDTO address;
    private String phone;

    private String creditCardNumber;
    private PaymentMethod paymentMethod;

    private RepresentativeDetailsDTO() {

    }

    public static RepresentativeDetailsDTO fromRepresentativeDetails(RepresentativeDetails representativeDetails) {
        RepresentativeDetailsDTO representativeDetailsDTO = new RepresentativeDetailsDTO();

        representativeDetailsDTO.firstName = representativeDetails.firstName();
        representativeDetailsDTO.lastName = representativeDetails.lastName();
        representativeDetailsDTO.email = representativeDetails.email();
        representativeDetailsDTO.address = AddressDTO.fromAddress(representativeDetails.address());
        representativeDetailsDTO.phone = representativeDetails.phone();

        representativeDetailsDTO.creditCardNumber = representativeDetails.creditCardNumber();
        representativeDetailsDTO.paymentMethod = representativeDetails.paymentMethod();

        return representativeDetailsDTO;
    }

    public String firstName() {
        return this.firstName;
    }

    public String lastName() {
        return this.lastName;
    }

    public String email() {
        return this.email;
    }

    public AddressDTO address() {
        return this.address;
    }

    public String phone() {
        return this.phone;
    }

    public String creditCardNumber() {
        return this.creditCardNumber;
    }

    public PaymentMethod paymentMethod() {
        return this.paymentMethod;
    }

    @Override
    public String toString() {
        return this.firstName + ' ' + this.lastName + "\r\n" + this.email + "\r\n" + this.phone + "\r\n" + this.address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final RepresentativeDetailsDTO that = (RepresentativeDetailsDTO) o;
        return Objects.equals(this.firstName, that.firstName) && Objects.equals(this.lastName, that.lastName) && Objects.equals(this.email, that.email) && Objects.equals(this.address, that.address) && Objects.equals(this.phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.firstName, this.lastName, this.email, this.address, this.phone);
    }
}
