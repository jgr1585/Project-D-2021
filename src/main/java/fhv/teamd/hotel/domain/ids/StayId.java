package fhv.teamd.hotel.domain.ids;


import fhv.teamd.hotel.domain.Stay;

public class StayId extends DomainId<Stay> {

    private StayId() {
    }

    public StayId(String id) {
        super(id);
    }
}
