package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.contactInfo.Address;
import fhv.teamd.hotel.domain.ids.OrganizationId;

import java.util.Objects;

public class Organization {

    private Long id;
    private OrganizationId domainId;

    private String organizationName;
    private Address address;
    private int discount;

    protected Organization() {
        // hibernate
    }

    public Organization(String organizationName, Address address, int discount) {
        this.organizationName = organizationName;
        this.address = address;
        this.discount = discount;
    }

    protected Long id() {
        return this.id;
    }

    public OrganizationId organizationId() {
        return this.domainId;
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Organization organization = (Organization) o;
        return Objects.equals(this.id, organization.id) && Objects.equals(this.domainId, organization.domainId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.domainId);
    }
}
