package fhv.teamd.hotel.application;

import fhv.teamd.hotel.application.dto.BillDTO;
import fhv.teamd.hotel.application.exceptions.InvalidIdException;
import fhv.teamd.hotel.domain.contactInfo.RepresentativeDetails;

import java.util.List;

public interface BillingService {

    void assignPayments(String fromBillId, List<String> descList, RepresentativeDetails billingAddress) throws InvalidIdException;

    BillDTO getBill(String stayId) throws InvalidIdException;

}
