package fhv.teamd.hotel.domain.repositories;

import fhv.teamd.hotel.domain.Organization;
import fhv.teamd.hotel.domain.ids.OrganizationId;

import java.util.List;
import java.util.Optional;

public interface OrganizationRepository {

    OrganizationId nextIdentity();

    List<Organization> getAll();

    Optional<Organization> findById(OrganizationId organizationId);

    void put(Organization organization);

}
