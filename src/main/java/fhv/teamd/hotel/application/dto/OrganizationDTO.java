package fhv.teamd.hotel.application.dto;

import fhv.teamd.hotel.domain.Organization;
import fhv.teamd.hotel.domain.contactInfo.Address;

import java.util.Objects;

public class OrganizationDTO {

    private String id;
    private String organizationName;
    private Address address;
    private int discount;

    private OrganizationDTO() {
    }

    //Test only
    @Deprecated
    public OrganizationDTO(String id, String organizationName, Address address, int discount) {
        this.id = id;
        this.organizationName = organizationName;
        this.address = address;
        this.discount = discount;
    }

    public String id() {
        return this.id;
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

    public static OrganizationDTO fromOrganization(Organization organization) {
        OrganizationDTO organizationDTO = new OrganizationDTO();

        organizationDTO.id = organization.organizationId() == null ? null : organization.organizationId().toString();
        organizationDTO.organizationName = organization.organizationName();
        organizationDTO.address = organization.address();
        organizationDTO.discount = organization.discount();

        return organizationDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final OrganizationDTO that = (OrganizationDTO) o;
        return Objects.equals(this.id, that.id) && Objects.equals(this.organizationName, that.organizationName) && Objects.equals(this.address, that.address) && Double.compare(that.discount, this.discount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.organizationName, this.address, this.discount);
    }
}
