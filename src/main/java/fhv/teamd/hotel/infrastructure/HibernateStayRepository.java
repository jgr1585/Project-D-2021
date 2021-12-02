package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.*;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.ids.StayId;
import fhv.teamd.hotel.domain.repositories.StayRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public class HibernateStayRepository implements StayRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public StayId nextIdentity() {
        return new StayId(UUID.randomUUID().toString());
    }

    @Override
    public List<Stay> getAll() {
        return this.entityManager.createQuery("select s from Stay s", Stay.class)
                .getResultList();
    }

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
    public int getNumberOfStayRoomsByCategory(CategoryId categoryId, LocalDateTime from, LocalDateTime until) {

        Long l = this.entityManager.createQuery(
                        "select count(r) from Stay s " +
                                "join s.rooms r " +
                                "where s.checkIn < :until and s.expectedCheckOut > :from " +
                                "and r.category.categoryId = :catId",
                        Long.class)
                .setParameter("from", from)
                .setParameter("until", until)
                .setParameter("catId", categoryId)
                .getSingleResult();

        return Optional.ofNullable(l).map(Long::intValue).orElse(0);

    }

    @Override
    public void put(Stay stay) {

        this.entityManager.persist(stay);

    }

    @Override
    public Optional<Stay> find(StayId stayId) {
        return this.entityManager
                .createQuery("SELECT s FROM Stay s WHERE s.stayId = :id", Stay.class)
                .setParameter("id", stayId)
                .getResultStream()
                .findFirst();
    }
}
