package fhv.teamd.hotel.domain.repositories;

import fhv.teamd.hotel.domain.Season;
import fhv.teamd.hotel.domain.ids.SeasonId;

import java.time.Month;
import java.util.List;

public interface SeasonRepository {

    SeasonId nextIdentity();

    List<Season> getAll();

    Season getSeasonFromMonth(Month month);
}
