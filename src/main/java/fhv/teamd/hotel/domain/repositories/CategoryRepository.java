package fhv.teamd.hotel.domain.repositories;

import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.ids.CategoryId;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    List<Category> getAll();

    Optional<Category> findById(CategoryId id);

}
