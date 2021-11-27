package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.Room;
import fhv.teamd.hotel.domain.Stay;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.ids.StayId;
import fhv.teamd.hotel.domain.repositories.StayRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
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

        TypedQuery<Stay> q
                = this.entityManager.createQuery("select s from Stay s", Stay.class);

        return q.getResultList();
    }

    @Override
    public List<Stay> staysWithOverlappingDuration(LocalDateTime from, LocalDateTime until) {

        TypedQuery<Stay> q = this.entityManager.createQuery(
                "select s from Stay s where (s.checkIn<:until and s.expectedCheckOut>:from)",
                Stay.class);

        q.setParameter("from", from);
        q.setParameter("until", until);

        return q.getResultList();
    }

    @Override
    public int getNumberOfStayRoomsByCategory(CategoryId categoryId, LocalDateTime from, LocalDateTime until) {
        int numberOfRooms = 0;

        for (Stay stay : this.staysWithOverlappingDuration(from, until)) {
            Set<Room> rooms = stay.rooms();
            for (Room room : rooms) {
                if (room.category().categoryId().equals(categoryId)) {
                    numberOfRooms++;
                }
            }
        }

        return numberOfRooms;
    }

    @Override
    public void put(Stay stay) {

        this.entityManager.persist(stay);

    }
}
