package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.Room;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class HibernateRoomRepository implements RoomRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Room> getAll() {
        return this.entityManager
                .createQuery("select r from Room r", Room.class)
                .getResultList();
    }

    @Override
    public List<Room> getByCategory(CategoryId categoryId) {

        TypedQuery<Room> q = this.entityManager
                .createQuery("select r from Room r where r.categoryId=:catId", Room.class);

        q.setParameter("catId", categoryId);

        return q.getResultList();
    }
}
