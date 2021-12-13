package fhv.teamd.hotel.domain.repositories;


import fhv.teamd.hotel.domain.Bill;
import fhv.teamd.hotel.domain.ids.BillId;

import java.util.List;
import java.util.Optional;

public interface BillRepository {

    BillId nextIdentity();

    List<Bill> getAll();

    Optional<Bill> findById(BillId bookingId);
}
