package fhv.teamd.hotel.application;

import fhv.teamd.hotel.application.dto.BillDTO;
import fhv.teamd.hotel.application.dto.BillEntryDTO;
import fhv.teamd.hotel.application.exceptions.InvalidIdException;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;
import fhv.teamd.hotel.domain.repositories.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.function.Predicate;

public interface BillingService {

    void assignPayments(String fromBillId, Predicate<BillEntryDTO> filter, RepresentativeDetails billingAddress) throws InvalidIdException;

    BillDTO getBill(String stayId) throws InvalidIdException;

}
