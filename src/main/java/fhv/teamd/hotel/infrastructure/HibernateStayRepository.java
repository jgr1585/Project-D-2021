package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.Stay;
import fhv.teamd.hotel.domain.ids.StayId;
import fhv.teamd.hotel.domain.repositories.StayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

@Repository
public class HibernateStayRepository implements StayRepository {

    @Autowired
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
    public void put(Stay stay) {

        this.entityManager.persist(stay);

    }
}
