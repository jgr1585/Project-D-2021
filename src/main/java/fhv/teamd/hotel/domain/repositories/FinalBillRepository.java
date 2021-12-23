package fhv.teamd.hotel.domain.repositories;

import fhv.teamd.hotel.domain.FinalBill;
import fhv.teamd.hotel.domain.ids.FinalBillId;

import java.util.List;
import java.util.Optional;

public interface FinalBillRepository {

    FinalBillId nextIdentity();

    List<FinalBill> getAll();

    Optional<FinalBill> findById(FinalBillId bookingId);

}
