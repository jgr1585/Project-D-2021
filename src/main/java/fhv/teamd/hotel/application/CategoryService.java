package fhv.teamd.hotel.application;

import fhv.teamd.hotel.application.dto.BookableCategoryDTO;
import fhv.teamd.hotel.application.dto.CategoryDTO;
import fhv.teamd.hotel.application.dto.RequestedStayDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategoryDTO> getAll();

    List<BookableCategoryDTO> getAvailableCategories(LocalDateTime from, LocalDateTime until);

    Optional<Boolean> isAvailable(RequestedStayDTO requestedStay);

}
