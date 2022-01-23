package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.*;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.ids.StayId;
import fhv.teamd.hotel.domain.repositories.StayRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class HibernateStayRepository extends HibernateBaseRepository<Stay, StayId> implements StayRepository {

    @Override
    public List<Stay> getActiveStays() {
        return this.entityManager
                .createQuery("SELECT s FROM Stay s where s.stayingState = :state", Stay.class)
                .setParameter("state", StayingState.CheckedIn)
                .getResultList();
    }

    @Override
    public List<Stay> activeStaysWithOverlappingDuration(LocalDateTime from, LocalDateTime until) {

        return this.entityManager.createQuery(
                "select s from Stay s where (s.checkIn < : until and s.expectedCheckOut > :from and s.stayingState = :state)",
                Stay.class)
                .setParameter("from", from)
                .setParameter("until", until)
                .setParameter("state", StayingState.CheckedIn)
                .getResultList();
    }

    @Override
    public int numberOfStayRoomsByCategory(CategoryId categoryId, LocalDateTime from, LocalDateTime until) {

        Long l = this.entityManager.createQuery(
                        "select count(r) from Stay s " +
                                "join s.rooms r " +
                                "where s.checkIn < :until and s.expectedCheckOut > :from " +
                                "and r.category.domainId = :catId",
                        Long.class)
                .setParameter("from", from)
                .setParameter("until", until)
                .setParameter("catId", categoryId)
                .getSingleResult();

        return l != null ? l.intValue() : 0;
    }

    @Override
    public void put(Stay stay) {

        this.entityManager.persist(stay);

    }

}
