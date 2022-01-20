package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.Room;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.ids.RoomId;
import fhv.teamd.hotel.domain.repositories.RoomRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HibernateRoomRepository extends HibernateBaseRepository<Room, RoomId> implements RoomRepository {

    @Override
    public List<Room> getByCategory(CategoryId categoryId) {
        return this.getByCategory(categoryId, Integer.MAX_VALUE);
    }

    @Override
    public List<Room> getByCategory(CategoryId categoryId, int max) {
        return this.entityManager
                .createQuery("select r from Room r where r.category.domainId=:catId", Room.class)
                .setParameter("catId", categoryId)
                .setMaxResults(max)
                .getResultList();
    }

}
