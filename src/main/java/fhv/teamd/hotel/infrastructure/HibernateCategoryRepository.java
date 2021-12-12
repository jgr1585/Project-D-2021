package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.repositories.CategoryRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class HibernateCategoryRepository extends HibernateBaseRepository<Category> implements CategoryRepository {

    @Override
    public Optional<Category> findById(CategoryId id) {
        return this.entityManager
                .createQuery("select c from Category c where c.categoryId=:id", Category.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
    }
}
