package fhv.teamd.hotel.domain.ids;

import fhv.teamd.hotel.domain.Organization;

public class OrganizationId extends DomainId<Organization> {
    private OrganizationId() {
    }

    public OrganizationId(String id) {
        super(id);
    }
}
