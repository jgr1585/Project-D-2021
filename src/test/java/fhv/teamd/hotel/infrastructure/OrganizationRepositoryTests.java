package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.DomainFactory;
import fhv.teamd.hotel.domain.Organization;
import fhv.teamd.hotel.domain.contactInfo.Address;
import fhv.teamd.hotel.domain.ids.OrganizationId;
import fhv.teamd.hotel.domain.repositories.OrganizationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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

    @Test
    void given_organization_when_findById_return_organizationById() {
        Optional<Organization> expected = BaseRepositoryData.organizations()
                .stream()
                .filter(b -> b.organizationId().toString().equals("dom-id-org-111"))
                .findFirst();

        Optional<Organization> actual = this.organizationRepository.findById(new OrganizationId("dom-id-org-111"));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void given_none_when_findById_return_EmptyOptional() {
        Assertions.assertEquals(Optional.empty(), this.organizationRepository.findById(DomainFactory.createOrganizationId()));
    }

    @Test
    void given_booking_when_putNewBooking_return_allBookings() {
        Organization newOrg = new Organization(
                this.organizationRepository.nextIdentity(),
                "Gantner Instruments",
                new Address("Montafoner Str. 4", "6780", "Schruns", "Austria"),
                20
        );

        List<Organization> expected = new ArrayList<>(List.of(newOrg));
        expected.addAll(BaseRepositoryData.organizations());

        this.organizationRepository.put(newOrg);
        this.entityManager.flush();

        List<Organization> actual = this.organizationRepository.getAll();

        Assertions.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }
}
