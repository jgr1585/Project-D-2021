package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.contactInfo.Address;
import fhv.teamd.hotel.domain.ids.OrganizationId;

public class Organization {

    private Long id;
    private OrganizationId domainId;

    private String organizationName;
    private Address address;
    private int discount;

    public Organization() {
        this(null, null, 0);
    }

    public Organization(String organizationName, Address address, int discount) {
        this.organizationName = organizationName;
        this.address = address;
        this.discount = discount;
    }
}
