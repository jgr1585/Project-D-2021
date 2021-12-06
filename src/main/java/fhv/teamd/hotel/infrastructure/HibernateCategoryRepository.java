package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.repositories.CategoryRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class HibernateCategoryRepository implements CategoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Category> getAll() {
        return this.entityManager
                .createQuery("select c from Category c", Category.class)
                .getResultList();
    }

    @Override
    public Optional<Category> findById(CategoryId id) {
        return this.entityManager
                .createQuery("select c from Category c where c.categoryId=:id", Category.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
    }
}
