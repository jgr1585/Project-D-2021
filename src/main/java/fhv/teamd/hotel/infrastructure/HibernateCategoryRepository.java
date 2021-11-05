package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class HibernateCategoryRepository implements CategoryRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Category> getAll() {
        return this.entityManager
                .createQuery("select c from Category c", Category.class)
                .getResultList();
    }
}
