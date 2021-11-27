package fhv.teamd.hotel.application.impl;

import fhv.teamd.hotel.application.CategoryService;
import fhv.teamd.hotel.application.dto.AvailableCategoryDTO;
import fhv.teamd.hotel.application.dto.CategoryDTO;
import fhv.teamd.hotel.domain.Category;
import fhv.teamd.hotel.domain.ids.CategoryId;
import fhv.teamd.hotel.domain.repositories.CategoryRepository;
import fhv.teamd.hotel.domain.services.RoomAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RoomAssignmentService roomAssignmentService;

    @Override
    public List<CategoryDTO> getAll() {

        List<Category> categories = this.categoryRepository.getAll();

        return categories
                .stream()
                .map(CategoryDTO::fromCategory)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<AvailableCategoryDTO> getAvailableCategories(LocalDateTime from, LocalDateTime until) {
        List<AvailableCategoryDTO> result = new ArrayList<>();

        for (Category cat : this.categoryRepository.getAll()) {

            // todo magic number xd
            int count = 99;
            result.add(new AvailableCategoryDTO(
                    cat.categoryId().toString(),
                    cat.title(),
                    count));
        }

        return result;
    }

    @Override
    public List<AvailableCategoryDTO> getAvailableCategoriesTest(LocalDateTime from, LocalDateTime until) {

        List<AvailableCategoryDTO> result = new ArrayList<>();

        for (Category cat : this.categoryRepository.getAll()) {
            int count = this.roomAssignmentService.getAmountOfAvailableCategory(cat.categoryId(), from, until);

            result.add(new AvailableCategoryDTO(
                    cat.categoryId().toString(),
                    cat.title(),
                    count));
        }

        return result;
    }

    @Override
    public boolean isAvailable(Map.Entry<String, Integer> categoryIdsAndAmounts, LocalDateTime from, LocalDateTime until) {

        // todo implement (out of sprint 1 scope)

        // get all booked rooms with their specific category id and their amount

        // Entry<categoriename, anzahl>



        /*
            select sum(number_of_rooms) from booking_for_category bfc
            where bfc.category_id = categoryId booking_id in (
                select id from booking b
                where b.check_in <= '2021-12-26 10:00:00' and b.check_out >= '2021-12-26 10:00:00'
            );
         */
        


        return true;
    }

    @Override
    public Optional<CategoryDTO> findCategoryById(String categoryId) {

        CategoryId id = new CategoryId(categoryId);

        Optional<Category> result = this.categoryRepository.findById(id);
        if (result.isEmpty()) {
            return Optional.empty();
        }

        Category category = result.get();

        return Optional.of(CategoryDTO.fromCategory(category));
    }
}
