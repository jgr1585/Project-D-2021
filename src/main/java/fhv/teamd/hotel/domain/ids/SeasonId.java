package fhv.teamd.hotel.domain.ids;

import fhv.teamd.hotel.domain.Season;

public class SeasonId extends DomainId<Season> {
    private SeasonId() {
    }

    public SeasonId(String id) {
        super(id);
    }

}
