package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.Organization;
import fhv.teamd.hotel.domain.ids.OrganizationId;
import fhv.teamd.hotel.domain.repositories.OrganizationRepository;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateOrganizationRepository extends HibernateBaseRepository<Organization, OrganizationId> implements OrganizationRepository {
    @Override
    public void put(Organization organization) {

        this.entityManager.persist(organization);

    }
}
