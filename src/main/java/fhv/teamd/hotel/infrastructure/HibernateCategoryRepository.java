package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Category> findById(CategoryId id) {
        TypedQuery<Category> q = this.entityManager
                .createQuery("select c from Category c where c.categoryId=:id", Category.class);

        q.setParameter("id", id);

        try {
            return Optional.ofNullable(q.getSingleResult());
        } catch (NoResultException x) {
            return Optional.empty();
        }

    }
}
