package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.Room;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.ids.RoomId;
import fhv.teamd.hotel.domain.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class HibernateRoomRepository implements RoomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Room> getAll() {
        return this.entityManager
                .createQuery("select r from Room r", Room.class)
                .getResultList();
    }

    @Override
    public List<Room> getByCategory(CategoryId categoryId) {
/*
        TypedQuery<Room> q = this.entityManager
                .createQuery("select r from Room r where r.categoryId=:catId", Room.class);

        q.setParameter("catId", categoryId);

        return q.getResultList();

 */

        return this.getAll().stream()
                .filter(r -> r.category().categoryId().equals(categoryId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Room> getById(RoomId roomId) {
        TypedQuery<Room> q = this.entityManager
                .createQuery("select r from Room r where r.roomId=:id", Room.class);

        q.setParameter("id", roomId);

        try {
            return Optional.ofNullable(q.getSingleResult());
        } catch (NoResultException x) {
            return Optional.empty();
        }
    }
}
