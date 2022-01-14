package fhv.teamd.hotel.application;

import fhv.teamd.hotel.application.dto.OrganizationDTO;
import fhv.teamd.hotel.domain.DomainFactory;
import fhv.teamd.hotel.domain.Organization;
import fhv.teamd.hotel.domain.contactInfo.OrganizationDetails;
import fhv.teamd.hotel.domain.ids.OrganizationId;
import fhv.teamd.hotel.domain.repositories.OrganizationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
public class OrganizationServiceTests {

    @Autowired
    private OrganizationService organizationService;

    @MockBean
    private OrganizationRepository organizationRepository;

    @Captor
    private ArgumentCaptor<Organization> actualOrganization;

    @Test
    void given_OrganizationList_when_getAll_then_return_all_Organizations() {
        // given
        List<Organization> organizations = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            organizations.add(DomainFactory.createOrganization());
        }

        Mockito.when(this.organizationRepository.getAll()).thenReturn(organizations);

        // when
        final List<OrganizationDTO> expected = organizations.stream().map(OrganizationDTO::fromOrganization).collect(Collectors.toList());
        final List<OrganizationDTO> actual = this.organizationService.getAll();

        // then
        Assertions.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    void given_Organizations_when_findCategoryById_then_return_Organization() {
        final Organization org1 = DomainFactory.createOrganization();
        final Organization org2 = DomainFactory.createOrganization();
        final OrganizationId nonExistingOrg = DomainFactory.createOrganizationId();

        Mockito.when(this.organizationRepository.findById(org1.organizationId())).thenReturn(Optional.of(org1));
        Mockito.when(this.organizationRepository.findById(org2.organizationId())).thenReturn(Optional.of(org2));

        Assertions.assertEquals(OrganizationDTO.fromOrganization(org1), this.organizationService.findOrganizationById(org1.organizationId().toString()).get());
        Assertions.assertEquals(OrganizationDTO.fromOrganization(org2), this.organizationService.findOrganizationById(org2.organizationId().toString()).get());
        Assertions.assertEquals(Optional.empty(), this.organizationService.findOrganizationById(nonExistingOrg.toString()));
    }

    @Test
    void given_OrganizationDetails_when_addOrganization_then_return_organizationId() {
        final Organization expected = DomainFactory.createOrganization();
        final OrganizationDetails expectedDetails = DomainFactory.createOrganizationDetailsFromOrganization(expected);

        Mockito.when(this.organizationRepository.nextIdentity()).thenReturn(expected.organizationId());

        final OrganizationId actualId = this.organizationService.add(expectedDetails);

        Mockito.verify(this.organizationRepository).put(this.actualOrganization.capture());

        Assertions.assertEquals(expected.organizationId(), actualId);
        Assertions.assertEquals(expected, this.actualOrganization.getValue());
    }
}