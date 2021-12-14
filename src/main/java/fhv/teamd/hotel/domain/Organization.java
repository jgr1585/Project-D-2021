package fhv.teamd.hotel.domain;

import fhv.teamd.hotel.domain.contactInfo.Address;

public class Organization {

    private String companyOrTravelAgencyName;
    private Address address;
    private int discount;

    public Organization() {
        this(null, null);
    }

    public Organization(String companyOrTravelAgencyName, Address address) {
        this.companyOrTravelAgencyName = companyOrTravelAgencyName;
        this.address = address;
    }


}
