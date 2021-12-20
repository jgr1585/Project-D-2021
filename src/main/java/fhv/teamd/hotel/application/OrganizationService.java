package fhv.teamd.hotel.application;

import fhv.teamd.hotel.application.dto.OrganizationDTO;

import java.util.List;
import java.util.Optional;

public interface OrganizationService {

    List<OrganizationDTO> getAll();

    Optional<OrganizationDTO> findOrganizationById(String organizationId);

}
