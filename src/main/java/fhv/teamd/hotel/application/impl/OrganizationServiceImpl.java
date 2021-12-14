package fhv.teamd.hotel.application.impl;

import fhv.teamd.hotel.application.OrganizationService;
import fhv.teamd.hotel.application.dto.OrganizationDTO;
import fhv.teamd.hotel.domain.Organization;
import fhv.teamd.hotel.domain.repositories.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class OrganizationServiceImpl implements OrganizationService {
    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public List<OrganizationDTO> getAll() {

        List<Organization> organizations = this.organizationRepository.getAll();

        return organizations
                .stream()
                .map(OrganizationDTO::fromOrganization)
                .collect(Collectors.toUnmodifiableList());
    }
}
