package fhv.teamd.hotel.infrastructure;

import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.repositories.CategoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public class CategoryRepositoryImpl {

    public List<Category> getAll() {
        return List.of(
                new Category("Single Bed", "adsf", 55),
                new Category("Double Bed", "fdaa", 70)
        );
    }

}
