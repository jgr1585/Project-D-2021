package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.Season;
import fhv.teamd.hotel.domain.ids.SeasonId;
import fhv.teamd.hotel.domain.repositories.SeasonRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.time.Month;

@Repository
public class HibernateSeasonRepository extends HibernateBaseRepository<Season, SeasonId> implements SeasonRepository {

    @SuppressWarnings("JpaQlInspection")
    @Override
    public Season getSeasonFromMonth(Month month) {
        try {
            return this.entityManager
                    .createQuery("select s from Season s where s.from = (SELECT MAX(s1.from) from Season s1 where s1.from <= :fromM)", Season.class)
                    .setParameter("fromM", month)
                    .getSingleResult();
        } catch (NoResultException e) {
             return this.entityManager
                    .createQuery("select s from Season s where s.from = (SELECT MAX(s1.from) from Season s1)", Season.class)
                    .getSingleResult();
        }
    }
}
