package fhv.teamd.hotel.domain.contactInfo;

import java.util.Objects;

public class GuestDetails {

    private final String name;
    private final String lastName;
    private final Address address;

    public GuestDetails(String name, String lastName, Address address) {
        this.name = name;
        this.lastName = lastName;
        this.address = address;
    }

    public String firstName() {
        return this.name;
    }

    public String lastName() {
        return this.lastName;
    }

    public Address address() {
        return this.address;
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
        return Objects.equals(this.name, that.name) && Objects.equals(this.lastName, that.lastName) && Objects.equals(this.address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.lastName, this.address);
    }
}
