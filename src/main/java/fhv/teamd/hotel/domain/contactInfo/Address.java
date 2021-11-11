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

    public String getStreet() {
        return this.street;
    }

    public String getZip() {
        return this.zip;
    }

    public String getCity() {
        return this.city;
    }

    public String getCountry() {
        return this.country;
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
