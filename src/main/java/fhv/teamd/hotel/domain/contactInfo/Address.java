package fhv.teamd.hotel.domain.contactInfo;

import java.util.Objects;

public class Address {

    public final String street;
    public final String zip;
    public final String city;
    public final String country;

    private Address() {
        this(null, null, null, null);
    }

    public Address(String street, String zip, String city, String country) {
        this.street = street;
        this.zip = zip;
        this.city = city;
        this.country = country;
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
        final Address address = (Address) o;
        return Objects.equals(this.street, address.street) && Objects.equals(this.zip, address.zip) && Objects.equals(this.city, address.city) && Objects.equals(this.country, address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.street, this.zip, this.city, this.country);
    }
}
