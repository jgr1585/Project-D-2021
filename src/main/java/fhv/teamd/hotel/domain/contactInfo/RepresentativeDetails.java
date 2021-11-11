package fhv.teamd.hotel.domain.contactInfo;

import java.util.Objects;

public class RepresentativeDetails {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final Address address;
    private final String phone;


    public RepresentativeDetails(String firstName, String lastName, String email, Address address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.phone = phone;
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
