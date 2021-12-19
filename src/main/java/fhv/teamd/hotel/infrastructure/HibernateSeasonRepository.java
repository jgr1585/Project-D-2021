package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.Season;
import fhv.teamd.hotel.domain.ids.SeasonId;
import fhv.teamd.hotel.domain.repositories.SeasonRepository;

import java.time.Month;

public class HibernateSeasonRepository extends HibernateBaseRepository<Season, SeasonId> implements SeasonRepository {
    @Override
    public Season getSeasonFromMonth(Month month) {
        Season season = this.entityManager
                .createQuery("select s from Season s where s.from = (SELECT MAX(s1.from) from Season s1 where s1.from <= :fromM)", Season.class)
                .setParameter("fromM", month.getValue())
                .getSingleResult();

        if (season == null) {
            season = this.entityManager
                    .createQuery("select s from Season s where s.from = (SELECT MAX(s1.from) from Season s1)", Season.class)
                    .getSingleResult();
        }

        return season;
    }
}
