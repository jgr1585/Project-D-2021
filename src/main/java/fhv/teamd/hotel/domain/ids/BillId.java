package fhv.teamd.hotel.domain.ids;

import fhv.teamd.hotel.domain.Bill;

public class BillId extends DomainId<Bill> {
    private BillId() {
    }

    public BillId(String id) {
        super(id);
    }
}
