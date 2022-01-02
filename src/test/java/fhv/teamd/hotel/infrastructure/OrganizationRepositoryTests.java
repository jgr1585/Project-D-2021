package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.Organization;
import fhv.teamd.hotel.domain.ids.OrganizationId;
import fhv.teamd.hotel.domain.repositories.OrganizationRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Transactional
public class OrganizationRepositoryTests {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void text_nextIdentity() {
        OrganizationId organizationId1 = this.organizationRepository.nextIdentity();
        OrganizationId organizationId2 = this.organizationRepository.nextIdentity();

        Assertions.assertNotNull(organizationId1);
        Assertions.assertNotNull(organizationId2);
        Assertions.assertNotNull(organizationId1.toString());
        Assertions.assertNotNull(organizationId2.toString());
        Assertions.assertNotEquals(organizationId1, organizationId2);
    }

    @Test
    void given_organization_when_getAll_return_all() {
        List<Organization> expected = BaseRepositoryData.organizations();
        List<Organization> actual = this.organizationRepository.getAll();

        Assertions.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }
}
