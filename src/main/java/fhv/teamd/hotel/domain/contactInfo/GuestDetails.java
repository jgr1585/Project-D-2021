package fhv.teamd.hotel.domain.contactInfo;

import java.util.Objects;

public class GuestDetails {
    private final boolean isSamePerson;
    private final GuestType guestType;

    private final String organizationName;
    private final int discount;

    private final String firstName;
    private final String lastName;
    private final Address address;

    private GuestDetails() {
        this(false, null,null, 0, null, null, null);
    }

    public GuestDetails(boolean isSamePerson, GuestType guestType, String organizationName, int discount, String firstName, String lastName, Address address) {
        this.isSamePerson = isSamePerson;
        this.guestType = guestType;
        this.organizationName = organizationName;
        this.discount = discount;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public boolean isSamePerson() {
        return this.isSamePerson;
    }

    public GuestType guestType() {
        return this.guestType;
    }

    public String organizationName() {
        return this.organizationName;
    }

    public int discount() {
        return this.discount;
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
