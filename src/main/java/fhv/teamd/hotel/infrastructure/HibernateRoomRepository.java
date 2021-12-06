package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.Room;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.ids.RoomId;
import fhv.teamd.hotel.domain.repositories.RoomRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

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
        return this.getByCategory(categoryId, Integer.MAX_VALUE);
    }

    @Override
    public List<Room> getByCategory(CategoryId categoryId, int max) {
        return this.entityManager
                .createQuery("select r from Room r where r.category.categoryId=:catId", Room.class)
                .setParameter("catId", categoryId)
                .setMaxResults(max)
                .getResultList();
    }

    @Override
    public Optional<Room> getById(RoomId roomId) {
        return this.entityManager
                .createQuery("select r from Room r where r.roomId=:id", Room.class)
                .setParameter("id", roomId)
                .getResultStream()
                .findFirst();
    }
}
