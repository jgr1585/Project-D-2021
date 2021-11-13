package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.Stay;
import fhv.teamd.hotel.domain.repositories.StayRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class HibernateStayRepository implements StayRepository {

    @Autowired
    private EntityManager entityManager;

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
