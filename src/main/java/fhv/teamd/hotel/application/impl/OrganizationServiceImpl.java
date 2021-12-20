package fhv.teamd.hotel.application.impl;

import fhv.teamd.hotel.application.OrganizationService;
import fhv.teamd.hotel.application.dto.CategoryDTO;
import fhv.teamd.hotel.application.dto.OrganizationDTO;
import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.Organization;
import fhv.teamd.hotel.domain.contactInfo.Address;
import fhv.teamd.hotel.domain.contactInfo.OrganizationDetails;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.ids.OrganizationId;
import fhv.teamd.hotel.domain.repositories.OrganizationRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    @Transactional
    public List<OrganizationDTO> getAll() {

        List<Organization> organizations = this.organizationRepository.getAll();

        return organizations
                .stream()
                .map(OrganizationDTO::fromOrganization)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional
    public Optional<OrganizationDTO> findOrganizationById(String organizationId) {
        Optional<Organization> result = this.organizationRepository.findById(new OrganizationId(organizationId));

        return result.map(OrganizationDTO::fromOrganization);
    }

    @Override
    @Transactional
    public OrganizationId add(OrganizationDetails details) {
        OrganizationId organizationId = this.organizationRepository.nextIdentity();

        Organization organization = new Organization(
                organizationId,
                details.organizationName(),
                details.address(),
                details.discount()
        );

        this.organizationRepository.put(organization);

        return organizationId;
    }
}
