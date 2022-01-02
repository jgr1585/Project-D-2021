package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.contactInfo.Address;
import fhv.teamd.hotel.domain.contactInfo.GuestDetails;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.ids.BillId;
import fhv.teamd.hotel.domain.ids.OrganizationId;
import fhv.teamd.hotel.domain.ids.StayId;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class Organization {

    private Long id;
    private OrganizationId domainId;

    private String organizationName;
    private Address address;
    private int discount;

    protected Organization() {
        // hibernate
    }

    public Organization(OrganizationId domainId, String organizationName, Address address, int discount) {
        this.domainId = domainId;
        this.organizationName = organizationName;
        this.address = address;
        this.discount = discount;
    }

    //Test only
    @Deprecated
    public Organization(Long id, OrganizationId domainId, String organizationName, Address address, int discount) {
        this.id = id;
        this.domainId = domainId;
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
        final Organization organization = (Organization) o;
        return Objects.equals(this.id, organization.id) && Objects.equals(this.domainId, organization.domainId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.domainId);
    }
}
