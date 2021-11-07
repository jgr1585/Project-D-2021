package fhv.teamd.hotel.application;

import fhv.teamd.hotel.application.dto.BookableCategoryDTO;
import fhv.teamd.hotel.application.dto.CategoryDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface CategoryService {

    List<CategoryDTO> getAll();

    List<BookableCategoryDTO> getAvailableCategories(LocalDateTime from, LocalDateTime until);

    boolean isAvailable(Map<String, Integer> categoryIdsAndAmounts,
                        LocalDateTime from, LocalDateTime until);

}
