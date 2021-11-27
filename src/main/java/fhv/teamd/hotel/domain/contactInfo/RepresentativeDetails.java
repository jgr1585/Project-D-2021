package fhv.teamd.hotel.domain.contactInfo;

import java.util.Objects;

public class RepresentativeDetails {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final Address address;
    private final String phone;

    private final String creditCardNumber;
    private final PaymentMethod paymentMethod;

    private RepresentativeDetails() {
        this(null, null, null, null, null, null, null);
    }

    public RepresentativeDetails(String firstName, String lastName, String email, Address address, String phone, String creditCardNumber, PaymentMethod paymentMethod) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.creditCardNumber = creditCardNumber;
        this.paymentMethod = paymentMethod;
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

    public Address address() {
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
        final RepresentativeDetails that = (RepresentativeDetails) o;
        return Objects.equals(this.firstName, that.firstName) && Objects.equals(this.lastName, that.lastName) && Objects.equals(this.email, that.email) && Objects.equals(this.address, that.address) && Objects.equals(this.phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.firstName, this.lastName, this.email, this.address, this.phone);
    }
}
