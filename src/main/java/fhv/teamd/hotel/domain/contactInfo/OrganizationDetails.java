package fhv.teamd.hotel.domain.contactInfo;

import java.util.Objects;

public class OrganizationDetails {
    private final String organizationName;
    private final Address address;
    private final int discount;

    public OrganizationDetails(String organizationName, Address address, int discount) {
        this.organizationName = organizationName;
        this.address = address;
        this.discount = discount;
    }

    public String organizationName() {
        return this.organizationName;
    }

    public Address address() {
        return this.address;
    }

    public int discount() {
        return this.discount;
    }

    @Override
    public String toString() {
        return this.organizationName + "\r\n" + this.address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final OrganizationDetails that = (OrganizationDetails) o;
        return this.discount == that.discount && Objects.equals(this.organizationName, that.organizationName) && Objects.equals(this.address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.organizationName, this.address, this.discount);
    }
}
