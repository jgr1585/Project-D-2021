package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.repositories.CategoryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateCategoryRepository extends HibernateBaseRepository<Category, CategoryId> implements CategoryRepository {
}
