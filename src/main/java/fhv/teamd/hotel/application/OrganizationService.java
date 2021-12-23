package fhv.teamd.hotel.application;

import fhv.teamd.hotel.application.dto.OrganizationDTO;
import fhv.teamd.hotel.domain.contactInfo.OrganizationDetails;
import fhv.teamd.hotel.domain.ids.OrganizationId;

import java.util.List;
import java.util.Optional;

public interface OrganizationService {

    OrganizationId add(OrganizationDetails organizationDetails);

    List<OrganizationDTO> getAll();

    Optional<OrganizationDTO> findOrganizationById(String organizationId);

}
