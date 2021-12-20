package fhv.teamd.hotel.domain.contactInfo;

import java.util.Objects;

public class GuestDetails {
    private final String firstName;
    private final String lastName;
    private final Address address;

    private GuestDetails() {
        this(null, null, null);
    }

    public GuestDetails(String firstName, String lastName, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public String firstName() {
        return this.firstName;
    }

    public String lastName() {
        return this.lastName;
    }

    public Address address() {
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
        final GuestDetails that = (GuestDetails) o;
        return Objects.equals(this.firstName, that.firstName) && Objects.equals(this.lastName, that.lastName) && Objects.equals(this.address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.firstName, this.lastName, this.address);
    }
}
