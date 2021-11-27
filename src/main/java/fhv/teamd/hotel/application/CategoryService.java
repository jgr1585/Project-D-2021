package fhv.teamd.hotel.application;

import fhv.teamd.hotel.application.dto.AvailableCategoryDTO;
import fhv.teamd.hotel.application.dto.CategoryDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategoryDTO> getAll();

    List<AvailableCategoryDTO> getAvailableCategories(LocalDateTime from, LocalDateTime until);

    Optional<CategoryDTO> findCategoryById(String categoryId);

}
